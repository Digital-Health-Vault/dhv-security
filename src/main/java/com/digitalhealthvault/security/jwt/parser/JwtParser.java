package com.digitalhealthvault.security.jwt.parser;

import com.digitalhealthvault.security.exception.JwtValidationException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class JwtParser {

    public SignedJWT parse(String token) {

        try {
            return SignedJWT.parse(token);

        } catch (ParseException ex) {

            throw new JwtValidationException(
                    "Invalid JWT format.",
                    ex
            );
        }

    }

}