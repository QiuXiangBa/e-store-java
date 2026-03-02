package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 结算单状态枚举 / Trade checkout order status enum.
 */
@Getter
public enum TradeCheckoutOrderStatusEnum {

    /**
     * 已创建 / Created.
     */
    CREATED(0),

    /**
     * 支付中（已创建 PaymentIntent）/ Pending pay.
     */
    PENDING_PAY(1),

    /**
     * 已支付 / Paid.
     */
    PAID(2),

    /**
     * 支付失败 / Pay failed.
     */
    PAY_FAILED(3),

    /**
     * 已关闭 / Closed.
     */
    CLOSED(4);

    private final Integer code;

    TradeCheckoutOrderStatusEnum(Integer code) {
        this.code = code;
    }

    public static boolean contains(Integer code) {
        if (code == null) {
            return false;
        }
        for (TradeCheckoutOrderStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
