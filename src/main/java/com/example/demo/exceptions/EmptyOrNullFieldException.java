package com.example.demo.exceptions;

public class EmptyOrNullFieldException extends RuntimeException {
    public EmptyOrNullFieldException(String message) {
        super(message);
    }
}
