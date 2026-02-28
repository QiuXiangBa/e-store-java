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
    CREATED(0);

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
