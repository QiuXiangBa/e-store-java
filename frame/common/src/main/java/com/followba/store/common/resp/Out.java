/*
 * AjaxResponse.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.common.resp;


import com.followba.store.common.constent.CodeEnum;
import lombok.Data;

/**
 * ajax response
 *
 * @param <T>
 * @author 祥霸
 * @since 1.0.0
 */
@Data
public class Out<T> {

    private int code;

    private String desc;

    private String enDesc;

    private T data;

    public Out(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Out(int code, String desc, String enDesc) {
        this.code = code;
        this.desc = desc;
        this.enDesc = enDesc;
    }

	public static Out<String> success() {
		return Out.buildResponse(CodeEnum.SUCCESS.code(), "", null);
	}

	public static <T> Out<T> success(T data) {
		return Out.buildResponse(CodeEnum.SUCCESS.code(), "", data);
	}

    public static <T> Out<T> build(CodeEnum errorCode) {
        return new Out<>(errorCode.code(), errorCode.msg());
    }

    public static <T> Out<T> build(int code, String msg) {
        return new Out<>(code, msg);
    }

    public static <T> Out<T> build(int code, String msg, String enDesc) {
        return new Out<>(code, msg, enDesc);
    }

    public static <T> Out<T> build(CodeEnum errorCode, T data) {
        return new Out<>(errorCode.code(), errorCode.msg(), data);
    }

    public boolean isSuccess() {
        return CodeEnum.SUCCESS.code() == this.code;
    }

    public static <T> Out<T> buildResponse(int code, String msg, T data) {
        return new Out<>(code, msg, data);
    }

    /**
     * 构造器
     */
    public Out() {
    }
    public Out(int code) {
        this.code = code;
    }
    public Out(final T data) {
        this.code = 200;
        this.data = data;
    }
    public Out(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

}
