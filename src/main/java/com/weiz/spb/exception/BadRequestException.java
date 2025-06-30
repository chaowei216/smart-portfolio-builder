package com.weiz.spb.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String message;

    public BadRequestException(String message) {

        super(message);
        this.message = message;
    }

    public BadRequestException(String message, Throwable ex) {

        super(message, ex);
        this.message = message;
    }
}
