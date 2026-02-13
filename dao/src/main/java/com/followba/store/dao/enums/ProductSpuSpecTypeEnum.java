package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 商品 SPU 规格类型枚举。
 */
@Getter
public enum ProductSpuSpecTypeEnum {

    /**
     * 单规格
     */
    SINGLE(0),

    /**
     * 多规格
     */
    MULTI(1);

    /**
     * 规格类型值
     */
    private final int code;

    ProductSpuSpecTypeEnum(int code) {
        this.code = code;
    }
}

