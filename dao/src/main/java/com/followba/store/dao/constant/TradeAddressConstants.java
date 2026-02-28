package com.followba.store.dao.constant;

import com.followba.store.common.constent.ErrorCode;

/**
 * 收货地址错误码与常量 / Trade address error code and constants.
 */
public enum TradeAddressConstants implements ErrorCode {

    /**
     * 地址不存在 / Address does not exist.
     */
    ADDRESS_NOT_EXISTS(63001, "地址不存在"),

    /**
     * 地址不属于当前用户 / Address does not belong to current user.
     */
    ADDRESS_NOT_BELONG_USER(63002, "地址不属于当前用户"),

    /**
     * 地址数量超过上限 / Address count exceeded.
     */
    ADDRESS_COUNT_EXCEEDED(63003, "地址数量超过上限");

    private final int code;

    private final String msg;

    TradeAddressConstants(int code, String msg) {
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

    public static final int ADDRESS_MAX_COUNT = 20;
}
