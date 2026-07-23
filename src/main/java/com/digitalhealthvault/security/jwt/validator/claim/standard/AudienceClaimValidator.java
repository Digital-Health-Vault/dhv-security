package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.properties.SecurityProperties;
import com.nimbusds.jwt.JWTClaimsSet;

public class AudienceClaimValidator implements ClaimValidator {

    private final SecurityProperties properties;

    public AudienceClaimValidator(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void validate(JWTClaimsSet claims) {

        if (!claims.getAudience().contains(properties.getAudience())) {

            throw new InvalidTokenException("Invalid JWT audience.");

        }
    }
}