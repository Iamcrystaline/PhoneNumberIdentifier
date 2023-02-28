package com.app.main.api;

public class UnknownPhoneNumberException extends RuntimeException {

    public UnknownPhoneNumberException(String message) {
        super(message);
    }
}
