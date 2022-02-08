package com.example.andy.configurationservice.exceptions;

public class UniqueElementException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UniqueElementException(String message) {
        super(message);
    }
}
