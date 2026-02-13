package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 状态： 0 开启 ，1 禁用
 */
@Getter
public enum StatusEnums {
    OPEN(0),

    DISABLED(1);

    ;

    private final Integer code;
    StatusEnums(Integer code){
        this.code = code;
    }
}
