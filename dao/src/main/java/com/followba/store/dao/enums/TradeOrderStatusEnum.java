package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 订单状态枚举 / Trade order status enum.
 */
@Getter
public enum TradeOrderStatusEnum {

    /**
     * 待支付 / Created.
     */
    CREATED(0, "待支付"),

    /**
     * 已支付 / Paid.
     */
    PAID(10, "已支付"),

    /**
     * 已取消 / Cancelled.
     */
    CANCELLED(20, "已取消"),

    /**
     * 已关闭 / Closed.
     */
    CLOSED(30, "已关闭"),

    /**
     * 已完成 / Completed.
     */
    COMPLETED(40, "已完成");

    private final Integer code;

    private final String desc;

    TradeOrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean contains(Integer code) {
        if (code == null) {
            return false;
        }
        for (TradeOrderStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static TradeOrderStatusEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (TradeOrderStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static boolean canCancel(Integer code) {
        return CREATED.code.equals(code);
    }

    public static boolean canPay(Integer code) {
        return CREATED.code.equals(code);
    }
}
