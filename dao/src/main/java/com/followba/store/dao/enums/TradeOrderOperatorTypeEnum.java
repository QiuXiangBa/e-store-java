package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 订单操作者类型枚举 / Trade order operator type enum.
 */
@Getter
public enum TradeOrderOperatorTypeEnum {

    /**
     * 用户操作 / User operator.
     */
    USER(0),

    /**
     * 系统操作 / System operator.
     */
    SYSTEM(1);

    private final Integer code;

    TradeOrderOperatorTypeEnum(Integer code) {
        this.code = code;
    }
}
