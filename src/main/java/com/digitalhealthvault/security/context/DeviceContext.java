package com.digitalhealthvault.security.context;

import com.digitalhealthvault.security.enums.DeviceType;

import java.io.Serializable;

public record DeviceContext(

        String deviceId,

        DeviceType deviceType,

        String ipAddress,

        String country,

        String language,

        String userAgent

) implements Serializable {
}