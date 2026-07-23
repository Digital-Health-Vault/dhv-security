package com.digitalhealthvault.security.service;

import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.model.TokenPair;


public interface TokenService {

    TokenPair generate(AuthenticationContext context);

    AuthenticationContext validate(String token);

}