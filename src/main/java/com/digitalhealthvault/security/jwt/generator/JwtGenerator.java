package com.digitalhealthvault.security.jwt.generator;

import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.crypto.NimbusSignerFactory;
import com.digitalhealthvault.security.crypto.SigningKeyProvider;
import com.digitalhealthvault.security.exception.JwtGenerationException;
import com.digitalhealthvault.security.jwt.builder.JwtClaimsBuilder;
import com.digitalhealthvault.security.jwt.builder.JwtHeaderBuilder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JwtGenerator {

    private final JwtClaimsBuilder claimsBuilder;
    private final JwtHeaderBuilder headerBuilder;
    private final SigningKeyProvider signingKeyProvider;
    private final NimbusSignerFactory signerFactory;

    public JwtGenerator(JwtClaimsBuilder claimsBuilder, JwtHeaderBuilder headerBuilder, SigningKeyProvider signingKeyProvider, NimbusSignerFactory signerFactory) {

        this.claimsBuilder = claimsBuilder;
        this.headerBuilder = headerBuilder;
        this.signingKeyProvider = signingKeyProvider;
        this.signerFactory = signerFactory;
    }

    public String generate(AuthenticationContext context) {

        JWTClaimsSet claims = claimsBuilder.build(context);

        JWSHeader header = headerBuilder.build(signingKeyProvider.getKeyId(), signingKeyProvider.getAlgorithm());

        SignedJWT signedJWT = new SignedJWT(header, claims);

        JWSSigner signer = signerFactory.create(signingKeyProvider);

        try {
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException ex) {
            throw new JwtGenerationException("Failed to generate JWT.", ex);
        }
    }
}