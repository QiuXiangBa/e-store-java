package com.followba.store.common.constent;

import lombok.Getter;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public enum LoginEnum {
    DEFAULT(0),

    WEB(10001),

    WS(11001),

    ;

    @Getter
    private final Integer code;

    LoginEnum(Integer code) {
        this.code = code;
    }
}
