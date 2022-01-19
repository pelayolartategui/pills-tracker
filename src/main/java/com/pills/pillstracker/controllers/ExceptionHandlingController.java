package com.pills.pillstracker.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private final MessageSource messages;

    public ExceptionHandlingController(MessageSource messageSource) {

        this.messages = messageSource;
    }

}
