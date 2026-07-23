package com.digitalhealthvault.security.model;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}