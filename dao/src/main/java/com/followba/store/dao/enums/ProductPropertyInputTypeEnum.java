package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 商品属性录入类型枚举 / Product property input type enum.
 */
@Getter
public enum ProductPropertyInputTypeEnum {

    /**
     * 手工输入 / Manual input.
     */
    MANUAL(0),

    /**
     * 预设值选择 / Preset value selection.
     */
    SELECT(1);

    /**
     * 类型值 / Type code.
     */
    private final int code;

    ProductPropertyInputTypeEnum(int code) {
        this.code = code;
    }
}
