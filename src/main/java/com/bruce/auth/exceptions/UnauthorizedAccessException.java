package com.bruce.auth.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    private final String errorCode;
    private final Object[] args;

    public UnauthorizedAccessException(String errorCode) {
        this(errorCode, null);
    }

    public UnauthorizedAccessException(String errorCode, Object[] args) {
        super(errorCode);
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}