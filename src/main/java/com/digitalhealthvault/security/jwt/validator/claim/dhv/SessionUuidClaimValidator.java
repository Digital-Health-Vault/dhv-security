package com.digitalhealthvault.security.jwt.validator.claim.dhv;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;

public class SessionUuidClaimValidator implements ClaimValidator {

    @Override
    public void validate(JWTClaimsSet claims) {
        try {
            if (claims.getStringClaim(JwtClaimNames.SESSION_UUID) == null) {
                throw new InvalidTokenException("Missing session UUID.");
            }
        } catch (ParseException e) {
            throw new InvalidTokenException("Invalid session UUID claim.", e);
        }
    }
}