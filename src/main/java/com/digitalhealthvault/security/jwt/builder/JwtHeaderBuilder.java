package com.digitalhealthvault.security.jwt.builder;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;

public class JwtHeaderBuilder {
    public JWSHeader build(String keyId,
                           JWSAlgorithm algorithm) {
        return new JWSHeader.Builder(algorithm)
                .type(JOSEObjectType.JWT)
                .keyID(keyId)
                .build();
    }
}