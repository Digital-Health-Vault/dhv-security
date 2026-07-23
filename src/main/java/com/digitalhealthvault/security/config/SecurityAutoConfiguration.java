package com.digitalhealthvault.security.config;


import com.digitalhealthvault.security.crypto.NimbusSignerFactory;
import com.digitalhealthvault.security.crypto.NimbusVerifierFactory;
import com.digitalhealthvault.security.crypto.RsaSigningKeyProvider;
import com.digitalhealthvault.security.crypto.SigningKeyProvider;
import com.digitalhealthvault.security.jwt.builder.JwtClaimsBuilder;
import com.digitalhealthvault.security.jwt.builder.JwtHeaderBuilder;
import com.digitalhealthvault.security.jwt.generator.JwtGenerator;
import com.digitalhealthvault.security.jwt.mapper.JwtMapper;
import com.digitalhealthvault.security.jwt.parser.JwtParser;
import com.digitalhealthvault.security.jwt.validator.ClaimsValidator;
import com.digitalhealthvault.security.jwt.validator.JwtValidator;
import com.digitalhealthvault.security.jwt.validator.SignatureValidator;
import com.digitalhealthvault.security.jwt.validator.claim.dhv.*;
import com.digitalhealthvault.security.jwt.validator.claim.standard.*;
import com.digitalhealthvault.security.properties.SecurityProperties;
import com.digitalhealthvault.security.provider.AuthenticationTokenProvider;
import com.digitalhealthvault.security.provider.JwtAuthenticationTokenProvider;
import com.digitalhealthvault.security.service.TokenService;
import com.digitalhealthvault.security.service.impl.NimbusTokenService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.time.Clock;
import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {
    @Bean
    public JwtClaimsBuilder jwtClaimsBuilder(SecurityProperties properties,
                                             Clock clock) {
        return new JwtClaimsBuilder(properties, clock);
    }

    @Bean
    public JwtHeaderBuilder jwtHeaderBuilder() {
        return new JwtHeaderBuilder();
    }

    @Bean
    public JwtGenerator jwtGenerator(
            JwtClaimsBuilder claimsBuilder,
            JwtHeaderBuilder headerBuilder,
            SigningKeyProvider signingKeyProvider,
            NimbusSignerFactory signerFactory) {

        return new JwtGenerator(
                claimsBuilder,
                headerBuilder,
                signingKeyProvider,
                signerFactory
        );
    }

    @Bean
    public JwtParser jwtParser() {
        return new JwtParser();
    }

    @Bean
    public SignatureValidator signatureValidator(
            SigningKeyProvider provider,
            NimbusVerifierFactory verifierFactory) {

        return new SignatureValidator(
                provider,
                verifierFactory
        );
    }

    @Bean
    public IssuerClaimValidator issuerClaimValidator(SecurityProperties properties) {
        return new IssuerClaimValidator(properties);
    }

    @Bean
    public AudienceClaimValidator audienceClaimValidator(SecurityProperties properties) {
        return new AudienceClaimValidator(properties);
    }

    @Bean
    public ExpirationClaimValidator expirationClaimValidator(Clock clock) {
        return new ExpirationClaimValidator(clock);
    }

    @Bean
    public NotBeforeClaimValidator notBeforeClaimValidator(Clock clock) {
        return new NotBeforeClaimValidator(clock);
    }

    @Bean
    public IssuedAtClaimValidator issuedAtClaimValidator(Clock clock) {
        return new IssuedAtClaimValidator(clock);
    }

    @Bean
    public ClaimsValidator claimsValidator(List<ClaimValidator> validators) {
        return new ClaimsValidator(validators);
    }

    @Bean
    public UserUuidClaimValidator userUuidClaimValidator() {
        return new UserUuidClaimValidator();
    }

    @Bean
    public UserCodeClaimValidator userCodeClaimValidator() {
        return new UserCodeClaimValidator();
    }

    @Bean
    public SessionUuidClaimValidator sessionUuidClaimValidator() {
        return new SessionUuidClaimValidator();
    }

    @Bean
    public LoginHistoryClaimValidator loginHistoryClaimValidator() {
        return new LoginHistoryClaimValidator();
    }

    @Bean
    public RolesClaimValidator rolesClaimValidator() {
        return new RolesClaimValidator();
    }

    @Bean
    public PermissionsClaimValidator permissionsClaimValidator() {
        return new PermissionsClaimValidator();
    }

    @Bean
    public JwtMapper jwtMapper() {
        return new JwtMapper();
    }

    @Bean
    public JwtValidator jwtValidator(
            JwtParser parser,
            SignatureValidator signatureValidator,
            ClaimsValidator claimsValidator,
            JwtMapper mapper) {

        return new JwtValidator(
                parser,
                signatureValidator,
                claimsValidator,
                mapper
        );
    }

    @Bean
    public TokenService tokenService(
            JwtGenerator jwtGenerator,
            JwtValidator jwtValidator) {

        return new NimbusTokenService(
                jwtGenerator,
                jwtValidator
        );
    }

    @Bean
    public AuthenticationTokenProvider authenticationTokenProvider(
            TokenService tokenService) {

        return new JwtAuthenticationTokenProvider(
                tokenService
        );
    }
}