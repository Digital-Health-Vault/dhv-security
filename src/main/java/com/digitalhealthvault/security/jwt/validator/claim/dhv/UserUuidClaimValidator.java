package com.digitalhealthvault.security.jwt.validator.claim.dhv;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;

public class UserUuidClaimValidator implements ClaimValidator {

    @Override
    public void validate(JWTClaimsSet claims) {
        try {
            String userUuid = claims.getStringClaim(JwtClaimNames.USER_UUID);
            if (userUuid == null) {
                throw new InvalidTokenException("Missing user UUID.");
            }
        } catch (java.text.ParseException ex) {
            throw new InvalidTokenException("Unable to read user UUID claim.", ex);
        }
    }
}