package com.bruce.auth.exceptions;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
