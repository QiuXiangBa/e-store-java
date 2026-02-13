package com.followba.store.objectstorage.core;

import com.followba.store.common.exception.BizException;
import com.followba.store.objectstorage.config.ObjectStorageProperties;
import com.followba.store.objectstorage.constant.ObjectStorageConstants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Log4j2
@Service
public class ObjectStorageService {

    @Resource
    private ObjectStorageProperties properties;

    @Resource
    private ObjectStorageClientFactory clientFactory;


    private ObjectStorageProviderType defaultProviderType;

    @PostConstruct
    public void init(){

        log.info("-- ObjectStorageService init -- ");

        this.defaultProviderType = ObjectStorageProviderType.fromCode(properties.getProvider());
        if (defaultProviderType == null) {
            throw new BizException(ObjectStorageConstants.PROVIDER_NOT_SUPPORTED,
                    "对象存储服务商不支持: " + properties.getProvider());
        }
    }

    public String upload(String objectKey, byte[] content, String contentType) {
        return clientFactory.getClient(defaultProviderType).upload(objectKey, content, contentType);
    }

    public void delete(String objectKey) {
        clientFactory.getClient(defaultProviderType).delete(objectKey);
    }

    public String getObjectUrl(String objectKey) {
        return clientFactory.getClient(defaultProviderType).getObjectUrl(objectKey);
    }

    public String createPresignedPutUrl(String objectKey, String contentType, Duration duration) {
        return clientFactory.getClient(defaultProviderType).createPresignedPutUrl(objectKey, contentType, duration);
    }

    public String createPresignedGetUrl(String objectKey, Duration duration) {
        return clientFactory.getClient(defaultProviderType).createPresignedGetUrl(objectKey, duration);
    }
}
