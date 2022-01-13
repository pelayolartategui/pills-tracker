package com.pills.pillstracker.controllers;

import com.pills.pillstracker.models.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registrationSubmit(@ModelAttribute @Valid UserDto user, Model model) {
        model.addAttribute("user", user);
        log.info(user.toString());
        return "login";
    }




}
