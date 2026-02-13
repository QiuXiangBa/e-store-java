package com.followba.store.objectstorage.core;

import java.time.Duration;

public interface ObjectStorageClient {

    ObjectStorageProviderType providerType();

    String upload(String objectKey, byte[] content, String contentType);

    void delete(String objectKey);

    String getObjectUrl(String objectKey);

    String createPresignedPutUrl(String objectKey, String contentType, Duration duration);

    String createPresignedGetUrl(String objectKey, Duration duration);
}
