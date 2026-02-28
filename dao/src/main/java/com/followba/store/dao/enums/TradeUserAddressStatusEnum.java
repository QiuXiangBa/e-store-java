package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 收货地址状态枚举 / Trade user address status enum.
 */
@Getter
public enum TradeUserAddressStatusEnum {

    /**
     * 启用 / Enabled.
     */
    ENABLE(0),

    /**
     * 禁用 / Disabled.
     */
    DISABLE(1);

    private final Integer code;

    TradeUserAddressStatusEnum(Integer code) {
        this.code = code;
    }
}
