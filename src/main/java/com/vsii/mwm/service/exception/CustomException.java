package com.vsii.mwm.service.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private String errorCode;
    private HttpStatus errorStatus;

    public CustomException(String message, HttpStatus errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public CustomException errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CustomException errorStatus(HttpStatus errorStatus) {
        this.errorStatus = errorStatus;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
