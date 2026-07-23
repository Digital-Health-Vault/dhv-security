package com.digitalhealthvault.security.service.impl;

import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.context.SessionContext;
import com.digitalhealthvault.security.enums.TokenType;
import com.digitalhealthvault.security.jwt.generator.JwtGenerator;
import com.digitalhealthvault.security.jwt.validator.JwtValidator;
import com.digitalhealthvault.security.model.TokenPair;
import com.digitalhealthvault.security.service.TokenService;

public class NimbusTokenService implements TokenService {

    private final JwtGenerator jwtGenerator;
    private final JwtValidator jwtValidator;

    public NimbusTokenService(JwtGenerator jwtGenerator, JwtValidator jwtValidator) {

        this.jwtGenerator = jwtGenerator;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public TokenPair generate(AuthenticationContext context) {
        AuthenticationContext tokenContext = updateTokenType(context, TokenType.ACCESS);

        String accessToken = jwtGenerator.generate(tokenContext);

        tokenContext = updateTokenType(context, TokenType.REFRESH);
        String refreshToken = jwtGenerator.generate(tokenContext);

        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public AuthenticationContext validate(String token) {
        return jwtValidator.validate(token);
    }

    /**
     * Creates a new AuthenticationContext with the required token type.
     */
    private AuthenticationContext updateTokenType(AuthenticationContext context, TokenType tokenType) {

        SessionContext oldSession = context.session();
        SessionContext updatedSession = new SessionContext(
                oldSession.sessionUuid(),
                oldSession.loginHistoryUuid(),
                tokenType
        );

        return new AuthenticationContext(
                context.user(),
                updatedSession,
                context.client(),
                context.device(),
                context.authorization()
        );
    }

}