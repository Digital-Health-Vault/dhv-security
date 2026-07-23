package com.digitalhealthvault.security.jwt.validator;

import com.digitalhealthvault.security.crypto.NimbusVerifierFactory;
import com.digitalhealthvault.security.crypto.SigningKeyProvider;
import com.digitalhealthvault.security.exception.InvalidTokenException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;

public class SignatureValidator {

    private final SigningKeyProvider signingKeyProvider;
    private final NimbusVerifierFactory verifierFactory;

    public SignatureValidator(SigningKeyProvider signingKeyProvider, NimbusVerifierFactory verifierFactory) {

        this.signingKeyProvider = signingKeyProvider;
        this.verifierFactory = verifierFactory;
    }

    public void validate(SignedJWT jwt) {

        JWSVerifier verifier = verifierFactory.create(signingKeyProvider);

        try {

            if (!jwt.verify(verifier)) {
                throw new InvalidTokenException("JWT signature validation failed.");
            }

        } catch (JOSEException ex) {

            throw new InvalidTokenException("Unable to verify JWT signature.", ex);

        }

    }

}