package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 二值开关枚举：0 关闭，1 开启。
 */
@Getter
public enum BinaryFlagEnum {

    /**
     * 关闭
     */
    OFF(0),

    /**
     * 开启
     */
    ON(1);

    /**
     * 开关值
     */
    private final int code;

    BinaryFlagEnum(int code) {
        this.code = code;
    }
}
