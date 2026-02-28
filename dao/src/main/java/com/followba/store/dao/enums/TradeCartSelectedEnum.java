package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 购物车勾选状态枚举 / Trade cart selected status enum.
 */
@Getter
public enum TradeCartSelectedEnum {

    /**
     * 未勾选 / Unselected.
     */
    UNSELECTED(0),

    /**
     * 已勾选 / Selected.
     */
    SELECTED(1);

    private final Integer code;

    TradeCartSelectedEnum(Integer code) {
        this.code = code;
    }

    public static boolean contains(Integer code) {
        if (code == null) {
            return false;
        }
        for (TradeCartSelectedEnum value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
