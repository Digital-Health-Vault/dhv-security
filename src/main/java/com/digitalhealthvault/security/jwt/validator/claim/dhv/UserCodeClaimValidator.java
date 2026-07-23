package com.digitalhealthvault.security.jwt.validator.claim.dhv;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;

public class UserCodeClaimValidator implements ClaimValidator {

    @Override
    public void validate(JWTClaimsSet claims) {
        try {
            if (claims.getStringClaim(JwtClaimNames.USER_CODE) == null) {
                throw new InvalidTokenException("Missing user code.");
            }
        } catch (ParseException e) {
            throw new InvalidTokenException("Invalid user code claim.", e);
        }
    }
}