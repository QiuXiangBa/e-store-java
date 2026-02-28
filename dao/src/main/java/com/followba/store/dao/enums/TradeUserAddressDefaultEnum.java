package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 收货地址默认状态枚举 / Trade user address default status enum.
 */
@Getter
public enum TradeUserAddressDefaultEnum {

    /**
     * 非默认 / Not default.
     */
    NOT_DEFAULT(0),

    /**
     * 默认地址 / Default address.
     */
    DEFAULT(1);

    private final Integer code;

    TradeUserAddressDefaultEnum(Integer code) {
        this.code = code;
    }
}
