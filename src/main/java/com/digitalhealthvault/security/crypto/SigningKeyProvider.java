package com.digitalhealthvault.security.crypto;

import com.nimbusds.jose.JWSAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface SigningKeyProvider {

    /**
     * Returns the private key used for signing JWTs.
     */
    PrivateKey getPrivateKey();

    /**
     * Returns the public key used for signature verification.
     */
    PublicKey getPublicKey();

    /**
     * Returns the key identifier (kid).
     */
    String getKeyId();

    /**
     * Returns the JWS algorithm.
     */
    JWSAlgorithm getAlgorithm();

}