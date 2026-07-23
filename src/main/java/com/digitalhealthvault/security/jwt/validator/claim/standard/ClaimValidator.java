package com.digitalhealthvault.security.jwt.validator.claim.standard;

import com.nimbusds.jwt.JWTClaimsSet;

public interface ClaimValidator {

    void validate(JWTClaimsSet claims);

}