package com.mysubscriptionsproject.common.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super("Bad request: " + message);
    }
}
