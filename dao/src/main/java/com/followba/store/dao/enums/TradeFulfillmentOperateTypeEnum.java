package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 履约操作类型枚举 / Trade fulfillment operate type enum.
 */
@Getter
public enum TradeFulfillmentOperateTypeEnum {

    /**
     * 创建履约 / Create fulfillment.
     */
    CREATE(0),

    /**
     * 发货 / Ship.
     */
    SHIP(1),

    /**
     * 物流节点更新 / Update logistics node.
     */
    LOGISTICS_NODE(2),

    /**
     * 用户签收 / User receive.
     */
    RECEIVE(3),

    /**
     * 关闭履约 / Close fulfillment.
     */
    CLOSE(4);

    private final Integer code;

    TradeFulfillmentOperateTypeEnum(Integer code) {
        this.code = code;
    }
}
