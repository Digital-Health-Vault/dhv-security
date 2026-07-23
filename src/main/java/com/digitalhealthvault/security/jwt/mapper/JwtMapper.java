package com.digitalhealthvault.security.jwt.mapper;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.context.AuthorizationContext;
import com.digitalhealthvault.security.context.ClientContext;
import com.digitalhealthvault.security.context.DeviceContext;
import com.digitalhealthvault.security.context.SessionContext;
import com.digitalhealthvault.security.context.UserContext;
import com.digitalhealthvault.security.enums.ClientType;
import com.digitalhealthvault.security.enums.DeviceType;
import com.digitalhealthvault.security.enums.LoginMethod;
import com.digitalhealthvault.security.enums.TokenType;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class JwtMapper {

    public AuthenticationContext map(SignedJWT jwt) {

        try {

            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            UserContext user = new UserContext(
                    UUID.fromString(claims.getStringClaim(JwtClaimNames.USER_UUID)),
                    claims.getStringClaim(JwtClaimNames.USER_CODE),
                    LoginMethod.valueOf(claims.getStringClaim(JwtClaimNames.LOGIN_METHOD))
            );

            SessionContext session = new SessionContext(
                    UUID.fromString(claims.getStringClaim(JwtClaimNames.SESSION_UUID)),
                    UUID.fromString(claims.getStringClaim(JwtClaimNames.LOGIN_HISTORY_UUID)),
                    TokenType.valueOf(claims.getStringClaim(JwtClaimNames.TOKEN_TYPE))
            );

            ClientContext client = new ClientContext(
                    claims.getStringClaim(JwtClaimNames.CLIENT_ID),
                    claims.getStringClaim(JwtClaimNames.CLIENT_NAME),
                    ClientType.valueOf(claims.getStringClaim(JwtClaimNames.CLIENT_TYPE)),
                    claims.getStringClaim(JwtClaimNames.CLIENT_VERSION)
            );

            DeviceContext device = new DeviceContext(
                    claims.getStringClaim(JwtClaimNames.DEVICE_ID),
                    DeviceType.valueOf(claims.getStringClaim(JwtClaimNames.DEVICE_TYPE)),
                    null,
                    claims.getStringClaim(JwtClaimNames.COUNTRY),
                    claims.getStringClaim(JwtClaimNames.LANGUAGE),
                    null
            );

            List<String> rolesList = claims.getStringListClaim(JwtClaimNames.ROLES);
            List<String> permissionsList = claims.getStringListClaim(JwtClaimNames.PERMISSIONS);

            Set<String> roles = rolesList == null ? Set.of() : Set.copyOf(rolesList);
            Set<String> permissions = permissionsList == null ? Set.of() : Set.copyOf(permissionsList);

            AuthorizationContext authorization = new AuthorizationContext(roles, permissions);

            return new AuthenticationContext(user, session, client, device, authorization);

        } catch (ParseException ex) {
            throw new IllegalStateException("Unable to map JWT claims.", ex);
        }
    }
}