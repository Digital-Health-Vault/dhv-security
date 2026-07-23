package com.digitalhealthvault.security.jwt.validator.claim.dhv;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;

import java.util.List;

public class RolesClaimValidator implements ClaimValidator {

    @Override
    public void validate(JWTClaimsSet claims) {
        try {
            List<String> roles = claims.getStringListClaim(JwtClaimNames.ROLES);

            if (roles == null || roles.isEmpty()) {
                throw new InvalidTokenException("No roles present in JWT.");
            }
        } catch (ParseException e) {
            throw new InvalidTokenException("Unable to read roles claim.", e);
        }
    }
}