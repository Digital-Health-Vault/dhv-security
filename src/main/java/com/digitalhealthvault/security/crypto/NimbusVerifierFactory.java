package com.digitalhealthvault.security.crypto;

import com.digitalhealthvault.security.exception.UnsupportedKeyException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

public class NimbusVerifierFactory {

    public JWSVerifier create(SigningKeyProvider provider) {

        PublicKey publicKey = provider.getPublicKey();

        if (!(publicKey instanceof RSAPublicKey rsaPublicKey)) {
            throw new UnsupportedKeyException(
                    "SigningKeyProvider does not contain an RSA public key."
            );
        }

        return new RSASSAVerifier(rsaPublicKey);
    }
}