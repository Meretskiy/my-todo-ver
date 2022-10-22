package com.meretskiy.mytodover.exceptions_handling;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
