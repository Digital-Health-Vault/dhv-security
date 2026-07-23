package com.digitalhealthvault.security.exception;

public class JwtGenerationException extends RuntimeException {
    public JwtGenerationException(String message) {
        super(message);
    }

    public JwtGenerationException(String message,
                                  Throwable cause) {
        super(message, cause);
    }
}