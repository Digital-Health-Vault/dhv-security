package com.digitalhealthvault.security.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class PemUtils {

    private static final String PRIVATE_KEY_BEGIN =
            "-----BEGIN PRIVATE KEY-----";

    private static final String PRIVATE_KEY_END =
            "-----END PRIVATE KEY-----";

    private static final String PUBLIC_KEY_BEGIN =
            "-----BEGIN PUBLIC KEY-----";

    private static final String PUBLIC_KEY_END =
            "-----END PUBLIC KEY-----";

    private PemUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static RSAPrivateKey readPrivateKey(InputStream inputStream)
            throws IOException, GeneralSecurityException {

        String pem = readPem(inputStream);

        String content = pem
                .replace(PRIVATE_KEY_BEGIN, "")
                .replace(PRIVATE_KEY_END, "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(content);

        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static RSAPublicKey readPublicKey(InputStream inputStream)
            throws IOException, GeneralSecurityException {

        String pem = readPem(inputStream);

        String content = pem
                .replace(PUBLIC_KEY_BEGIN, "")
                .replace(PUBLIC_KEY_END, "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(content);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static String readPem(InputStream inputStream)
            throws IOException {

        return new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}