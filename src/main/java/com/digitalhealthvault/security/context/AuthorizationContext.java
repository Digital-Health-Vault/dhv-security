package com.digitalhealthvault.security.context;

import java.io.Serializable;
import java.util.Set;

public record AuthorizationContext(

        Set<String> roles,

        Set<String> permissions

) implements Serializable {

    public AuthorizationContext {

        roles = roles == null
                ? Set.of()
                : Set.copyOf(roles);

        permissions = permissions == null
                ? Set.of()
                : Set.copyOf(permissions);

    }

}