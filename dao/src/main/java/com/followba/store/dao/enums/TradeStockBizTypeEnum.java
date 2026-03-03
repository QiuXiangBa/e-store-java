package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 库存流水业务类型枚举 / Trade stock log business type enum.
 */
@Getter
public enum TradeStockBizTypeEnum {

    /**
     * 订单创建扣减 / Deduct stock when creating order.
     */
    ORDER_CREATE_DEDUCT(1),

    /**
     * 订单取消回补 / Restore stock when canceling order.
     */
    ORDER_CANCEL_RESTORE(2),

    /**
     * 订单关闭回补 / Restore stock when closing order.
     */
    ORDER_CLOSE_RESTORE(3);

    private final Integer code;

    TradeStockBizTypeEnum(Integer code) {
        this.code = code;
    }
}
