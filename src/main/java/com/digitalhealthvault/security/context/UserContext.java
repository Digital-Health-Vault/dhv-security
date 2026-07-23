package com.digitalhealthvault.security.context;

import com.digitalhealthvault.security.enums.LoginMethod;

import java.io.Serializable;
import java.util.UUID;

public record UserContext(

        UUID userUuid,

        String userCode,

        LoginMethod loginMethod

) implements Serializable {
}