# DHV Security

> Enterprise Security Library for the Digital Health Vault Platform

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-green)
![JWT](https://img.shields.io/badge/JWT-RS256-orange)
![Status](https://img.shields.io/badge/Status-v1.0.0-success)

---

# Overview

DHV Security is the central security library used across all Digital Health Vault (DHV) microservices.

It provides a standardized implementation for:

- JWT Generation
- JWT Validation
- RSA Key Management
- Authentication Context
- Token Generation
- Token Validation
- Common Security Components

This library ensures that every DHV microservice follows the same authentication and authorization standards without duplicating security logic.

---

# Design Goals

The library was designed with the following principles:

- Centralized security implementation
- Immutable authentication context
- Stateless JWT authentication
- Pluggable cryptographic providers
- Spring Boot auto configuration
- Microservice friendly
- Zero business logic
- Easy future migration to AWS KMS / Vault

---

# Architecture

```
                 dhv-auth-service

                        │
                        ▼

        AuthenticationTokenProvider
                        │
                        ▼

               NimbusTokenService
              ┌─────────┴─────────┐
              │                   │
              ▼                   ▼
       JwtGenerator         JwtValidator
              │                   │
              ▼                   ▼
         Signed JWT     AuthenticationContext
```

The security library is completely independent of any individual microservice.

---

# Project Structure

```
src/main/java
└── com.digitalhealthvault.security
    ├── config
    ├── constants
    ├── context
    ├── crypto
    ├── exception
    ├── jwt
    ├── model
    ├── properties
    ├── provider
    ├── service
    └── util
```

---

# Authentication Flow

## Generation Flow

```
Client Login
│
▼
dhv-auth-service
│
▼
AuthenticationContext
│
▼
DHV Security
│
▼
Access Token
Refresh Token
│
▼
Client
```

## Validation Flow

```
JWT
│
▼
AuthenticationTokenProvider
│
▼
TokenService
│
▼
JwtValidator
│
▼
AuthenticationContext
```

---

## Login Sequence

```text
Client
   │
   │ Login Request
   ▼
dhv-auth-service
   │
   │ Validate Credentials
   ▼
AuthenticationContext
   │
   ▼
DHV Security
   │
   ▼
TokenPair
   │
   ▼
Client
```

# Public API

The following classes form the public API of the library.

| Class                       | Purpose                           |
|-----------------------------|-----------------------------------|
| AuthenticationTokenProvider | Main entry point                  |
| TokenService                | Token operations                  |
| AuthenticationContext       | Authenticated user context        |
| TokenPair                   | Generated access & refresh tokens |
| SecurityProperties          | Library configuration             |

Everything else should be considered an internal implementation detail.

---

Example:

```
@Autowired
private AuthenticationTokenProvider tokenProvider;

TokenPair tokenPair =
        tokenProvider.generate(authenticationContext);

AuthenticationContext authentication =
        tokenProvider.validate(jwt);
```

# JWT Claims

The library generates standardized JWT claims.

### Standard Claims

| Claim | Description     |
|-------|-----------------|
| iss   | Issuer          |
| sub   | Subject         |
| aud   | Audience        |
| exp   | Expiration      |
| iat   | Issued At       |
| nbf   | Not Before      |
| jti   | JWT ID          |

### DHV Claims

| Claim              | Description         |
|--------------------|---------------------|
| user_uuid          | User UUID           |
| user_code          | User Code           |
| login_method       | Login Method        |
| session_uuid       | Session UUID        |
| login_history_uuid | Login History UUID  |
| token_type         | Access / Refresh    |
| client_id          | Client ID           |
| client_name        | Client Name         |
| client_type        | Web / Mobile        |
| client_version     | Application Version |
| device_id          | Device Identifier   |
| device_type        | Device Type         |
| country            | Country             |
| language           | Preferred Language  |
| roles              | User Roles          |
| permissions        | User Permissions    |

---

# Configuration

Example:

```yaml
dhv:
  security:
    issuer: dhv-auth-service
    audience: dhv-platform
    key-id: dhv-key-001
    algorithm: RS256
    private-key: classpath:keys/private.pem
    public-key: classpath:keys/public.pem
    access-token-validity: 900
    refresh-token-validity: 2592000
```

---

# Installation

## Maven

```xml
<dependency>
    <groupId>com.digitalhealthvault</groupId>
    <artifactId>dhv-security</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

---

## Parent POM

If using the DHV Parent POM:

```xml
<parent>
    <groupId>com.digitalhealthvault</groupId>
    <artifactId>dhv-parent</artifactId>
    <version>1.0.0</version>
</parent>
```

# Spring Boot Auto Configuration

DHV Security is a Spring Boot Starter style library.

Adding the dependency automatically registers:

- AuthenticationTokenProvider
- TokenService
- JwtGenerator
- JwtValidator
- ClaimsValidator
- SigningKeyProvider
- Nimbus Signer / Verifier
- Clock

No additional Java configuration is required.

# Usage

## Generate Access & Refresh Tokens

Inject the `AuthenticationTokenProvider` into your service:

```
@Autowired
private AuthenticationTokenProvider tokenProvider;

AuthenticationContext authenticationContext = ...;

TokenPair tokenPair = tokenProvider.generate(authenticationContext);

String accessToken = tokenPair.accessToken();
String refreshToken = tokenPair.refreshToken();
```

## Validate JWT

```
@Autowired
private AuthenticationTokenProvider tokenProvider;

AuthenticationContext authentication = tokenProvider.validate(accessToken);
```

If the token is valid, an immutable `AuthenticationContext` is returned.

If the token is invalid, an appropriate security exception is thrown.

## Example Authentication Flow

```text
User Login
      │
      ▼
Validate Credentials
      │
      ▼
Create AuthenticationContext
      │
      ▼
DHV Security
      │
      ▼
Generate TokenPair
      │
      ▼
Return Access Token
Return Refresh Token
```

---

# Authentication Context

The library converts a validated JWT into a strongly typed immutable `AuthenticationContext`.

```text
AuthenticationContext
│
├── UserContext
│     ├── userUuid
│     ├── userCode
│     └── loginMethod
│
├── SessionContext
│     ├── sessionUuid
│     ├── loginHistoryUuid
│     └── tokenType
│
├── ClientContext
│     ├── clientId
│     ├── clientName
│     ├── clientType
│     └── clientVersion
│
├── DeviceContext
│     ├── deviceId
│     ├── deviceType
│     ├── country
│     └── language
│
└── AuthorizationContext
      ├── roles
      └── permissions
```

## Why AuthenticationContext?

Instead of every service parsing JWT claims directly:

```
String userUuid = jwt.getClaim("user_uuid");
```

services simply use:

```
authenticationContext.user().userUuid();
```

This provides:

- Type safety
- Better readability
- No dependency on JWT implementation
- Easier testing
- Cleaner business logic

---

# Cryptography

| Component      | Current | Future              |
| -------------- | ------- | ------------------- |
| Algorithm      | RSA     | RSA / EC / EdDSA    |
| Signature      | RS256   | ES256 / EdDSA       |
| Key Storage    | PEM     | AWS KMS             |
| Secret Manager | Local   | AWS Secrets Manager |
| JWT Library    | Nimbus  | Nimbus              |

---

## Key Management

Current implementation loads keys from PEM files:

- `classpath:keys/private.pem`
- `classpath:keys/public.pem`

Future versions will support:

- AWS KMS
- AWS Secrets Manager
- HashiCorp Vault
- Azure Key Vault
- Google Secret Manager

without changing the public API.

---

# Exception Handling

The library throws strongly typed exceptions.

| Exception | Description |
|-----------|-------------|
| InvalidTokenException | Invalid JWT |
| JwtGenerationException | Error while generating JWT |
| JwtValidationException | Error while validating JWT |
| UnsupportedKeyException | Unsupported key type |
| SecurityConfigurationException | Invalid security configuration |

The consuming microservice is responsible for:

- Logging
- HTTP Status Mapping
- Error Response Generation

Example:

```
try {
    AuthenticationContext authentication = tokenProvider.validate(token);
} catch (InvalidTokenException ex) {
    // Return HTTP 401 Unauthorized
}
```

---

# Thread Safety

All context objects are immutable Java Records.

```
public record UserContext(...)
```

Benefits:

- Immutable
- Thread-safe
- No setters
- No shared mutable state
- Safe for concurrent environments

---

# Internal Architecture

```text
                    AuthenticationTokenProvider
                                 │
                                 ▼
                         TokenService
                                 │
                                 ▼
                    NimbusTokenService
                     ┌──────────┴──────────┐
                     │                     │
                     ▼                     ▼
              JwtGenerator          JwtValidator
                     │                     │
                     ▼                     ▼
                 Signed JWT     AuthenticationContext
```

The application interacts only with the provider layer.

JWT implementation details remain internal.

---

# Dependencies

Core dependencies:

- Java 21
- Spring Boot
- Spring Security
- Nimbus JOSE + JWT
- Jakarta Validation
- Lombok

---

# Security Considerations

DHV Security follows several secure-by-default principles.

- JWTs are digitally signed using RSA.
- Private keys are never embedded in source code.
- AuthenticationContext is immutable.
- Token validation verifies:
    - Signature
    - Expiration
    - Issuer
    - Audience
    - Not Before
    - Required DHV claims
- Business services never parse JWT claims directly.

# Testing

Recommended unit tests:

- JwtGeneratorTest
- JwtValidatorTest
- JwtParserTest
- JwtMapperTest
- ClaimsValidatorTest
- NimbusTokenServiceTest
- RsaSigningKeyProviderTest
- PemUtilsTest

Target code coverage: 80%+

---

# Versioning

The project follows Semantic Versioning: `MAJOR.MINOR.PATCH`

Examples:

| Version | Description     |
|---------|-----------------|
| 1.0.0   | Initial Release |
| 1.0.1   | Bug Fix         |
| 1.1.0   | New Feature     |
| 2.0.0   | Breaking Change |

---

# Design Principles

DHV Security follows these engineering principles:

- Single Responsibility Principle
- Dependency Injection
- Immutable Domain Objects
- Stateless Authentication
- Separation of Concerns
- Framework Agnostic Core Design
- Pluggable Cryptography
- Secure by Default
- Microservice First

---

# Integration

This library is intended to be used by:

| Repository | Purpose |
|------------|---------|
| dhv-auth-service | Authentication & Token Issuance |
| dhv-gateway-service | Token Validation |
| dhv-consent-service | Authorization |
| dhv-fhir-service | User Authentication |
| dhv-notification-service | Authenticated Operations |
| dhv-ai-service | Secure AI Requests |

---

# Roadmap

Future planned enhancements:

## v1.1

- SecurityContextHolder
- Spring Security Authentication Filter
- Better Exception Hierarchy
- Improved Test Coverage

## v1.2

- Token Revocation
- Refresh Token Rotation
- Key Rotation
- JWKS Endpoint

## v2.0

- AWS KMS Integration
- Vault Integration
- Multi-Tenant Support
- OAuth2/OpenID Connect
- EdDSA / ES256 Support
- Security Metrics
- Audit Events
- Distributed Key Management


# Contributing

DHV Security is an internal Digital Health Vault library.

Contribution guidelines:

1. Create a feature branch.
2. Follow the project's coding standards.
3. Maintain backward compatibility.
4. Add or update unit tests.
5. Submit a Pull Request for review.
6. Ensure all CI checks pass before merging.

---

# Release Status

Current Version

```
1.0.0
```

Status

- Stable API
- Production Ready
- Internal Use

# Maintainer

Digital Health Vault Engineering Team

# License

Copyright © 2026 Digital Health Vault.

This project is part of the Digital Health Vault platform.

All Rights Reserved.