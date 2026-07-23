package com.digitalhealthvault.security.jwt.validator;

import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.jwt.mapper.JwtMapper;
import com.digitalhealthvault.security.jwt.parser.JwtParser;
import com.nimbusds.jwt.SignedJWT;

public class JwtValidator {

    private final JwtParser parser;
    private final SignatureValidator signatureValidator;
    private final ClaimsValidator claimsValidator;
    private final JwtMapper mapper;

    public JwtValidator(JwtParser parser, SignatureValidator signatureValidator, ClaimsValidator claimsValidator, JwtMapper mapper) {
        this.parser = parser;
        this.signatureValidator = signatureValidator;
        this.claimsValidator = claimsValidator;
        this.mapper = mapper;
    }

    public AuthenticationContext validate(String token) {
        SignedJWT jwt = parser.parse(token);

        signatureValidator.validate(jwt);

        claimsValidator.validate(jwt);

        return mapper.map(jwt);
    }
}