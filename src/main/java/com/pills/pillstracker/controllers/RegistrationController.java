package com.pills.pillstracker.controllers;

import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.metrics.PillsTrackerMetrics;
import com.pills.pillstracker.models.daos.User;
import com.pills.pillstracker.models.dtos.UserDto;
import com.pills.pillstracker.services.UserService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;


@Slf4j
@Controller
public class RegistrationController {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MessageSource messages;
    private final MeterRegistry meterRegistry;

    public RegistrationController(ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                                  UserService userService, MessageSource messages,
                                  MeterRegistry meterRegistry) {

        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.messages = messages;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registrationSubmit(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {

        log.debug("Registration request for User: {}", userDto);
        if (result.hasErrors()) {
            return "register";
        }
        model.addAttribute("user", userDto);
        try {
            User user = convertUserToEntity(userDto);
            userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException e) {
            result.addError(getError(e));
            return "register";
        }
        log.debug("New user with username: {} registered", userDto.getUsername());
        meterRegistry.counter(PillsTrackerMetrics.PT_NUM_SUCCESS_USER_REGISTRIES).increment();

        return "login";
    }

    private User convertUserToEntity(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    private ObjectError getError(UserAlreadyExistException e) {

        return new ObjectError("user", messages.getMessage(e.getMessageCode(), null, LocaleContextHolder.getLocale()));
    }

}
