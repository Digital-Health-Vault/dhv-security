package com.digitalhealthvault.security.context;

import com.digitalhealthvault.security.enums.ClientType;

import java.io.Serializable;

public record ClientContext(

        String clientId,

        String clientName,

        ClientType clientType,

        String version

) implements Serializable {
}