package com.niit.UserRegistrationService.exception;

public class DuplicateSpaceException extends RuntimeException {
    public DuplicateSpaceException(String message) {
        super(message);
    }
}
