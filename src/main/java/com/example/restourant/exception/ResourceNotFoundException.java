package com.example.restourant.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor dengan 1 parameter
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor dengan 2 parameter
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor dengan 3 parameter
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'",
                resourceName, fieldName, fieldValue));
    }
}