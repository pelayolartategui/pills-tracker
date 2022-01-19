package com.pills.pillstracker.exceptions;

import lombok.Getter;


@Getter
public class PillsTrackerException extends Exception {

    private final String messageCode;

    public PillsTrackerException(String messageCode) {

        super(messageCode);
        this.messageCode = messageCode;
    }

}
