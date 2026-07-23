package com.digitalhealthvault.security.exception;

public class TokenExpiredException extends JwtValidationException {

    public TokenExpiredException(String message) {
        super(message);
    }
}