package com.pills.pillstracker.controllers;

import com.pills.pillstracker.dtos.UserDto;
import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.metrics.PillsTrackerMetrics;
import com.pills.pillstracker.services.UserService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    private final UserService userService;
    private final MessageSource messages;
    private final MeterRegistry meterRegistry;

    public RegistrationController(UserService userService, MessageSource messages,
                                  MeterRegistry meterRegistry) {

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

        if (result.hasErrors()) {
            return "register";
        }
        model.addAttribute("user", userDto);
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException e) {
            result.addError(getError(e));
            return "register";
        }
        meterRegistry.counter(PillsTrackerMetrics.PT_NUM_SUCCESS_REGISTRY).increment();

        return "login";
    }

    private ObjectError getError(UserAlreadyExistException e) {

        return new ObjectError("user", messages.getMessage(e.getMessageCode(), null, LocaleContextHolder.getLocale()));
    }

}
