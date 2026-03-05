package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 履约操作者类型枚举 / Trade fulfillment operator type enum.
 */
@Getter
public enum TradeFulfillmentOperatorTypeEnum {

    /**
     * 管理员 / Admin operator.
     */
    ADMIN(0),

    /**
     * 用户 / User operator.
     */
    USER(1),

    /**
     * 系统 / System operator.
     */
    SYSTEM(2);

    private final Integer code;

    TradeFulfillmentOperatorTypeEnum(Integer code) {
        this.code = code;
    }
}
