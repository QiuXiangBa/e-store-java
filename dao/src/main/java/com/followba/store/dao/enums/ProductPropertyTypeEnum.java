package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 商品属性类型枚举 / Product property type enum.
 */
@Getter
public enum ProductPropertyTypeEnum {

    /**
     * 展示属性（用于详情展示） / Display attribute for product details.
     */
    DISPLAY(0),

    /**
     * 销售属性（用于 SKU 组合） / Sales attribute for SKU combinations.
     */
    SALES(1);

    /**
     * 类型值 / Type code.
     */
    private final int code;

    ProductPropertyTypeEnum(int code) {
        this.code = code;
    }
}
