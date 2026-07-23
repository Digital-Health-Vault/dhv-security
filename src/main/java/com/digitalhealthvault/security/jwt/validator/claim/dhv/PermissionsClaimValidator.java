package com.digitalhealthvault.security.jwt.validator.claim.dhv;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.digitalhealthvault.security.jwt.validator.claim.standard.ClaimValidator;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;

import java.util.List;

public class PermissionsClaimValidator implements ClaimValidator {

    @Override
    public void validate(JWTClaimsSet claims) {
        try {
            List<String> permissions = claims.getStringListClaim(JwtClaimNames.PERMISSIONS);

            if (permissions == null) {
                throw new InvalidTokenException("Permissions claim missing.");
            }
        } catch (ParseException e) {
            throw new InvalidTokenException("Unable to read permissions claim.", e);
        }
    }
}