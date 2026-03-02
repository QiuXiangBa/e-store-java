package com.followba.store.dao.constant;

import com.followba.store.common.constent.ErrorCode;

/**
 * 支付域错误码与常量 / Payment domain error codes and constants.
 */
public enum PaymentConstants implements ErrorCode {

    ORDER_NOT_EXISTS(61901, "订单不存在"),
    ORDER_NOT_BELONG_USER(61902, "订单不属于当前用户"),
    ORDER_STATUS_INVALID(61903, "订单状态不允许支付"),
    ORDER_ALREADY_PAID(61904, "订单已支付"),
    PAYMENT_ALREADY_EXISTS(61905, "该订单已有支付单");

    private final int code;
    private final String msg;

    PaymentConstants(int code, String msg) {
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
}
