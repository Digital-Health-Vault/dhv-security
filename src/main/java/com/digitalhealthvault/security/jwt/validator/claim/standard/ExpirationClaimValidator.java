package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

public class ExpirationClaimValidator implements ClaimValidator {

    private final Clock clock;

    public ExpirationClaimValidator(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void validate(JWTClaimsSet claims) {
        Date expiry = claims.getExpirationTime();

        if (expiry == null) {
            throw new InvalidTokenException("JWT expiration is missing.");
        }

        if (expiry.toInstant().isBefore(Instant.now(clock))) {
            throw new InvalidTokenException("JWT has expired.");
        }
    }
}