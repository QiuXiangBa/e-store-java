package com.followba.store.objectstorage.core;

import com.followba.store.common.exception.BizException;
import com.followba.store.objectstorage.constant.ObjectStorageConstants;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ObjectStorageClientFactory {

    private final Map<ObjectStorageProviderType, ObjectStorageClient> clientMap;

    public ObjectStorageClientFactory(List<ObjectStorageClient> clients) {
        this.clientMap = new EnumMap<>(ObjectStorageProviderType.class);
        for (ObjectStorageClient client : clients) {
            this.clientMap.put(client.providerType(), client);
        }
    }

    public ObjectStorageClient getClient(ObjectStorageProviderType providerType) {
        ObjectStorageClient client = clientMap.get(providerType);
        if (client == null) {
            throw new BizException(ObjectStorageConstants.PROVIDER_NOT_SUPPORTED,
                    "对象存储服务商不支持: " + providerType);
        }
        return client;
    }
}
