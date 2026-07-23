package com.digitalhealthvault.security.provider;

import com.digitalhealthvault.security.context.AuthenticationContext;

public interface AuthenticationTokenProvider {

    String generateAccessToken(AuthenticationContext context);

    String generateRefreshToken(AuthenticationContext context);

    AuthenticationContext validate(String token);

}