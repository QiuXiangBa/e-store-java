package com.followba.store.objectstorage.constant;

import com.followba.store.common.constent.ErrorCode;

public enum ObjectStorageConstants implements ErrorCode {

    PROVIDER_NOT_SUPPORTED(63001, "对象存储服务商不支持"),
    S3_CONFIG_INVALID(63002, "S3 配置不完整"),
    UPLOAD_FAILED(63003, "对象上传失败"),
    DELETE_FAILED(63004, "对象删除失败"),
    PRESIGN_FAILED(63005, "对象签名失败"),
    OBJECT_KEY_INVALID(63006, "对象 key 非法");

    private final int code;
    private final String msg;

    ObjectStorageConstants(int code, String msg) {
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
