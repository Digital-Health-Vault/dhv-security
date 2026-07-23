package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

public class NotBeforeClaimValidator implements ClaimValidator {

    private final Clock clock;

    public NotBeforeClaimValidator(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void validate(JWTClaimsSet claims) {
        Date notBefore = claims.getNotBeforeTime();

        if (notBefore == null) {
            return;
        }

        if (notBefore.toInstant().isAfter(Instant.now(clock))) {
            throw new InvalidTokenException("JWT is not yet valid.");
        }
    }
}