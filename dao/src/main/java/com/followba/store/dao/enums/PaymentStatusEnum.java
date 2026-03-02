package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 支付单状态枚举 / Payment status enum.
 */
@Getter
public enum PaymentStatusEnum {

    /**
     * 待支付 / Pending.
     */
    PENDING(0),

    /**
     * 支付成功 / Succeeded.
     */
    SUCCEEDED(1),

    /**
     * 支付失败 / Failed.
     */
    FAILED(2),

    /**
     * 已取消/关闭 / Canceled.
     */
    CANCELED(3);

    private final Integer code;

    PaymentStatusEnum(Integer code) {
        this.code = code;
    }

    public static boolean isTerminal(Integer code) {
        if (code == null) {
            return false;
        }
        // FAILED 允许后续重试并回正为 SUCCEEDED，因此不作为终态
        // FAILED can be retried and eventually turned into SUCCEEDED.
        return SUCCEEDED.getCode().equals(code)
                || CANCELED.getCode().equals(code);
    }
}
