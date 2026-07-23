package com.digitalhealthvault.security.crypto;

import com.digitalhealthvault.security.properties.SecurityProperties;
import com.digitalhealthvault.security.util.PemUtils;
import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaSigningKeyProvider implements SigningKeyProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    private final SecurityProperties properties;

    public RsaSigningKeyProvider(
            SecurityProperties properties,
            ResourceLoader resourceLoader) {

        this.properties = properties;

        try {

            Resource privateKeyResource =
                    resourceLoader.getResource(
                            properties.getPrivateKeyLocation());

            Resource publicKeyResource =
                    resourceLoader.getResource(
                            properties.getPublicKeyLocation());

            this.privateKey = PemUtils.readPrivateKey(
                    privateKeyResource.getInputStream());

            this.publicKey = PemUtils.readPublicKey(
                    publicKeyResource.getInputStream());

        } catch (IOException | GeneralSecurityException ex) {

            throw new IllegalStateException(
                    "Failed to initialize RSA signing keys.",
                    ex);

        }

    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String getKeyId() {
        return properties.getKeyId();
    }

    @Override
    public JWSAlgorithm getAlgorithm() {
        return JWSAlgorithm.RS256;
    }

}