package com.digitalhealthvault.security.context;

import com.digitalhealthvault.security.enums.TokenType;

import java.io.Serializable;
import java.util.UUID;

public record SessionContext(

        UUID sessionUuid,

        UUID loginHistoryUuid,

        TokenType tokenType

) implements Serializable {
}