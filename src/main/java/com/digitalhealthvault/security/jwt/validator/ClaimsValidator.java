package com.digitalhealthvault.security.jwt.validator;

import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.List;

public class ClaimsValidator {

    private final List<ClaimValidator> validators;

    public ClaimsValidator(List<ClaimValidator> validators) {
        this.validators = validators;
    }

    public void validate(SignedJWT jwt) {

        JWTClaimsSet claims;

        try {
            claims = jwt.getJWTClaimsSet();
        } catch (ParseException ex) {
            throw new IllegalStateException("Unable to parse JWT claims.", ex);
        }

        validators.forEach(v -> v.validate(claims));
    }
}