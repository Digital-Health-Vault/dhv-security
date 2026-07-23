package com.digitalhealthvault.security.provider;

import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.model.TokenPair;
import com.digitalhealthvault.security.service.TokenService;

public class JwtAuthenticationTokenProvider
        implements AuthenticationTokenProvider {

    private final TokenService tokenService;

    public JwtAuthenticationTokenProvider(
            TokenService tokenService) {

        this.tokenService = tokenService;
    }

    @Override
    public String generateAccessToken(
            AuthenticationContext context) {

        TokenPair pair = tokenService.generate(context);
        return pair.accessToken();
    }

    @Override
    public String generateRefreshToken(
            AuthenticationContext context) {

        TokenPair pair = tokenService.generate(context);
        return pair.refreshToken();
    }

    @Override
    public AuthenticationContext validate(
            String token) {

        return tokenService.validate(token);
    }

}