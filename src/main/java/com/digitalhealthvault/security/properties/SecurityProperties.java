package com.digitalhealthvault.security.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "dhv.security")
public class SecurityProperties {

    /**
     * JWT issuer.
     */
    @NotBlank
    private String issuer;

    /**
     * JWT audience.
     */
    @NotBlank
    private String audience;

    /**
     * JWT Key Identifier (kid).
     */
    @NotBlank
    private String keyId;

    /**
     * Private key resource location.
     *
     * Examples:
     * classpath:keys/private_key.pem
     * file:/opt/dhv/keys/private_key.pem
     */
    @NotBlank
    private String privateKeyLocation;

    /**
     * Public key resource location.
     *
     * Examples:
     * classpath:keys/public_key.pem
     * file:/opt/dhv/keys/public_key.pem
     */
    @NotBlank
    private String publicKeyLocation;

    /**
     * Access token validity in seconds.
     */
    @Min(60)
    private long accessTokenValidity;

    /**
     * Refresh token validity in seconds.
     */
    @Min(300)
    private long refreshTokenValidity;
}