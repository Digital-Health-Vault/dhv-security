package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.properties.SecurityProperties;
import com.nimbusds.jwt.JWTClaimsSet;

public class IssuerClaimValidator implements ClaimValidator {

    private final SecurityProperties properties;

    public IssuerClaimValidator(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void validate(JWTClaimsSet claims) {
        String issuer = claims.getIssuer();

        if (!properties.getIssuer().equals(issuer)) {
            throw new InvalidTokenException(
                    "Invalid JWT issuer."
            );
        }
    }
}