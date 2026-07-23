package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

public class IssuedAtClaimValidator implements ClaimValidator {

    private final Clock clock;

    public IssuedAtClaimValidator(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void validate(JWTClaimsSet claims) {
        Date issuedAt = claims.getIssueTime();

        if (issuedAt == null) {
            throw new InvalidTokenException("JWT issue time missing.");
        }

        if (issuedAt.toInstant().isAfter(Instant.now(clock))) {
            throw new InvalidTokenException("JWT issue time is invalid.");
        }
    }
}