package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 订单操作类型枚举 / Trade order operate type enum.
 */
@Getter
public enum TradeOrderOperateTypeEnum {

    /**
     * 创建订单 / Create order.
     */
    CREATE(0),

    /**
     * 取消订单 / Cancel order.
     */
    CANCEL(1),

    /**
     * 支付成功 / Pay success.
     */
    PAY_SUCCESS(2),

    /**
     * 系统关闭 / Auto close.
     */
    AUTO_CLOSE(3),

    /**
     * 用户签收 / User receive.
     */
    RECEIVE(4);

    private final Integer code;

    TradeOrderOperateTypeEnum(Integer code) {
        this.code = code;
    }
}
