package com.followba.store.objectstorage.core;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ObjectStorageProviderType {

    AWS_S3("aws-s3");

    private final String code;

    ObjectStorageProviderType(String code) {
        this.code = code;
    }

    public static ObjectStorageProviderType fromCode(String code) {
        return Arrays.stream(values())
                .filter(item -> item.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
