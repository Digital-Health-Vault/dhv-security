package com.digitalhealthvault.security.constants;

public final class JwtClaimNames {
    private JwtClaimNames() {
    }

    //User Constants
    public static final String USER_UUID = "user_uuid";

    public static final String USER_CODE = "user_code";

    public static final String LOGIN_METHOD = "login_method";

    //Session Constants
    public static final String SESSION_UUID = "session_uuid";

    public static final String LOGIN_HISTORY_UUID = "login_history_uuid";

    public static final String TOKEN_TYPE = "token_type";

    //Client Constants
    public static final String CLIENT_ID = "client_id";

    public static final String CLIENT_NAME = "client_name";

    public static final String CLIENT_TYPE = "client_type";

    public static final String CLIENT_VERSION = "client_version";

    //Device Constants
    public static final String DEVICE_ID = "device_id";

    public static final String DEVICE_TYPE = "device_type";

    public static final String COUNTRY = "country";

    public static final String LANGUAGE = "language";

    //Authorization Constants
    public static final String ROLES = "roles";

    public static final String PERMISSIONS = "permissions";
}