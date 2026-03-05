package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 履约状态枚举 / Trade fulfillment status enum.
 */
@Getter
public enum TradeFulfillmentStatusEnum {

    /**
     * 待发货 / Waiting for shipment.
     */
    WAIT_SHIP(0, "待发货"),

    /**
     * 已发货 / Shipped.
     */
    SHIPPED(10, "已发货"),

    /**
     * 运输中 / In transit.
     */
    IN_TRANSIT(20, "运输中"),

    /**
     * 派送中 / Out for delivery.
     */
    OUT_FOR_DELIVERY(30, "派送中"),

    /**
     * 已签收 / Signed.
     */
    SIGNED(40, "已签收"),

    /**
     * 已关闭 / Closed.
     */
    CLOSED(50, "已关闭");

    private final Integer code;

    private final String desc;

    TradeFulfillmentStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TradeFulfillmentStatusEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        for (TradeFulfillmentStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static boolean contains(Integer code) {
        return of(code) != null;
    }

    public static boolean canShip(Integer code) {
        return WAIT_SHIP.code.equals(code);
    }

    public static boolean canReceive(Integer code) {
        return SHIPPED.code.equals(code)
                || IN_TRANSIT.code.equals(code)
                || OUT_FOR_DELIVERY.code.equals(code);
    }

    public static boolean canAppendLogisticsNode(Integer code) {
        return SHIPPED.code.equals(code)
                || IN_TRANSIT.code.equals(code)
                || OUT_FOR_DELIVERY.code.equals(code);
    }
}
