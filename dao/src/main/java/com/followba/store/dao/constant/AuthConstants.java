package com.followba.store.dao.constant;

import com.followba.store.common.constent.ErrorCode;

public enum AuthConstants implements ErrorCode {

    AUTH_LOGIN_USER_NOT_EXISTS(62001, "账号不存在"),
    AUTH_LOGIN_PASSWORD_INVALID(62002, "账号或密码错误"),
    AUTH_LOGIN_USER_DISABLED(62003, "账号已禁用"),
    AUTH_USERNAME_EXISTS(62004, "账号已存在");

    private final int code;
    private final String msg;

    AuthConstants(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    public static final short USER_TYPE_ADMIN = 1;

    public static final short USER_STATUS_ENABLED = 0;

    public static final short USER_STATUS_DISABLED = 1;

    public static final short DEFAULT_TENANT_ID = 0;

    public static final short DEFAULT_NOT_DELETED = 0;

    public static final String CLIENT_ID_ADMIN = "admin-ui";

    public static final String DEFAULT_SCOPES = "[]";

    public static final String ROLE_ADMIN = "admin";

    public static final String PERMISSION_ALL = "*:*:*";

    public static final String TOKEN_PREFIX_BEARER = "Bearer ";

    public static final String BCRYPT_PREFIX_2A = "$2a$";

    public static final String BCRYPT_PREFIX_2B = "$2b$";

    public static final String BCRYPT_PREFIX_2Y = "$2y$";

    public static final long ACCESS_TOKEN_EXPIRE_MILLIS = 7L * 24 * 60 * 60 * 1000;

    public static final long REFRESH_TOKEN_EXPIRE_MILLIS = 30L * 24 * 60 * 60 * 1000;
}
