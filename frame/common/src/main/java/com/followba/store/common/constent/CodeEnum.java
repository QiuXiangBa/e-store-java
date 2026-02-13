/*
 * DistResponseCodeEnum.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.common.constent;

/**
 * 全局状态码
 *
 * @author 祥霸
 * @since 1.0.0
 */
public enum CodeEnum implements ErrorCode {

    /** 成功*/
    SUCCESS(200, "OK"),

    /** 未知异常*/
    ERROR(500, "系统开了会小差"),

    /** 请求参数有误*/
    PARAMS_BAD(400, "参数错误"),

    /** 未登录*/
    NO_LOGIN(401, "未登录"),

    SING_ERROR(402, "签名错误"),

    /** 无操作权限*/
    NO_PERMISSION(403, "无操作权限"),

    TIMEOUT_OFFLINE(15, "超时踢下线"),

    METHOD_NOT_ALLOWED(405, "请求方法不正确"),

    BEAN_METHOD_IS_NULL(406, "功能暂未开放"),

    ;

    private final int code;

    private final String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
