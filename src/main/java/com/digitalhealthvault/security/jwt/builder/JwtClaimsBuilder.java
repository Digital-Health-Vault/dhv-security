package com.digitalhealthvault.security.jwt.builder;

import com.digitalhealthvault.security.constants.JwtClaimNames;
import com.digitalhealthvault.security.context.AuthenticationContext;
import com.digitalhealthvault.security.properties.SecurityProperties;
import com.nimbusds.jwt.JWTClaimsSet;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtClaimsBuilder {
    private final SecurityProperties properties;
    private final Clock clock;

    public JwtClaimsBuilder(SecurityProperties properties,
                            Clock clock) {
        this.properties = properties;
        this.clock = clock;
    }

    public JWTClaimsSet build(AuthenticationContext context) {

        Instant now = Instant.now(clock);
        Instant expiry = now.plusSeconds(properties.getAccessTokenValidity());

        return new JWTClaimsSet.Builder()
                /*
                 * RFC 7519 Standard Claims
                 */
                .issuer(properties.getIssuer())
                .audience(properties.getAudience())
                .subject(context.user().userCode())
                .jwtID(UUID.randomUUID().toString())
                .issueTime(Date.from(now))
                .notBeforeTime(Date.from(now))
                .expirationTime(Date.from(expiry))

                /*
                 * User
                 */
                .claim(JwtClaimNames.USER_UUID,
                        context.user().userUuid().toString())

                .claim(JwtClaimNames.USER_CODE,
                        context.user().userCode())

                .claim(JwtClaimNames.LOGIN_METHOD,
                        context.user().loginMethod().name())

                /*
                 * Session
                 */
                .claim(JwtClaimNames.SESSION_UUID,
                        context.session().sessionUuid().toString())

                .claim(JwtClaimNames.LOGIN_HISTORY_UUID,
                        context.session().loginHistoryUuid().toString())

                .claim(JwtClaimNames.TOKEN_TYPE,
                        context.session().tokenType().name())

                /*
                 * Client
                 */
                .claim(JwtClaimNames.CLIENT_ID,
                        context.client().clientId())

                .claim(JwtClaimNames.CLIENT_NAME,
                        context.client().clientName())

                .claim(JwtClaimNames.CLIENT_TYPE,
                        context.client().clientType().name())

                .claim(JwtClaimNames.CLIENT_VERSION,
                        context.client().version())

                /*
                 * Device
                 */
                .claim(JwtClaimNames.DEVICE_ID,
                        context.device().deviceId())

                .claim(JwtClaimNames.DEVICE_TYPE,
                        context.device().deviceType().name())

                .claim(JwtClaimNames.COUNTRY,
                        context.device().country())

                .claim(JwtClaimNames.LANGUAGE,
                        context.device().language())

                /*
                 * Authorization
                 */
                .claim(JwtClaimNames.ROLES,
                        context.authorization().roles())

                .claim(JwtClaimNames.PERMISSIONS,
                        context.authorization().permissions())

                .build();
    }
}