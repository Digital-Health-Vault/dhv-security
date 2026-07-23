package com.digitalhealthvault.security.crypto;

import com.digitalhealthvault.security.exception.UnsupportedKeyException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;

import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;

public class NimbusSignerFactory {

    public JWSSigner create(SigningKeyProvider provider) {

        PrivateKey privateKey = provider.getPrivateKey();

        if (!(privateKey instanceof RSAPrivateKey rsaPrivateKey)) {
            throw new UnsupportedKeyException(
                    "SigningKeyProvider does not contain an RSA private key."
            );
        }

        return new RSASSASigner(rsaPrivateKey);
    }
}