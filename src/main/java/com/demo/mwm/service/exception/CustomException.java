package com.demo.mwm.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class
 */
public class CustomException extends RuntimeException {
    private HttpStatus errorStatus;
    private String[] args;

    /**
     * Constructs a new CustomException with the specified HTTP status, message, and message arguments.
     *
     * @param errorStatus The HTTP status associated with the exception.
     * @param message     The detail message.
     * @param args        The array of arguments for multi-language messages.
     */
    public CustomException(HttpStatus errorStatus, String message, String... args) {
        super(message);
        this.args = args;
        this.errorStatus = errorStatus;
    }

    public CustomException errorStatus(HttpStatus errorStatus) {
        this.errorStatus = errorStatus;
        return this;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
