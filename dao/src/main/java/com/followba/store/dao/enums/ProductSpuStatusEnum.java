package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 商品 SPU 状态枚举。
 */
@Getter
public enum ProductSpuStatusEnum {

    /**
     * 上架（可售）
     */
    ENABLE(1),

    /**
     * 下架（仓库中）
     */
    DISABLE(0),

    /**
     * 回收站
     */
    RECYCLE(-1);

    /**
     * 状态值
     */
    private final int code;

    ProductSpuStatusEnum(int code) {
        this.code = code;
    }

    /**
     * 判断状态码是否在枚举定义中。
     *
     * @param code 状态码
     * @return true-合法，false-不合法
     */
    public static boolean contains(Integer code) {
        if (code == null) {
            return false;
        }
        for (ProductSpuStatusEnum value : values()) {
            if (value.code == code) {
                return true;
            }
        }
        return false;
    }
}
