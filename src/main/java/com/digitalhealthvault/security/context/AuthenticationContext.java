package com.digitalhealthvault.security.context;

import java.io.Serializable;

public record AuthenticationContext(

        UserContext user,

        SessionContext session,

        ClientContext client,

        DeviceContext device,

        AuthorizationContext authorization

) implements Serializable {
}