package com.followba.store.admin.service.impl;

import com.followba.store.admin.service.SystemFileService;
import com.followba.store.admin.vo.in.SystemFilePresignedDownloadUrlIn;
import com.followba.store.admin.vo.in.SystemFilePresignedUploadUrlIn;
import com.followba.store.admin.vo.out.SystemFilePresignedDownloadUrlOut;
import com.followba.store.admin.vo.out.SystemFilePresignedUploadUrlOut;
import com.followba.store.objectstorage.core.ObjectStorageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class SystemFileServiceImpl implements SystemFileService {

    private static final String DEFAULT_PATH_PREFIX = "admin/upload";

    @Resource
    private ObjectStorageService objectStorageService;

    @Override
    public SystemFilePresignedUploadUrlOut getPresignedUploadUrl(SystemFilePresignedUploadUrlIn in) {
        String objectKey = buildObjectKey(in.getPathPrefix(), in.getFileName());

        SystemFilePresignedUploadUrlOut out = new SystemFilePresignedUploadUrlOut();
        out.setObjectKey(objectKey);
        out.setUploadUrl(objectStorageService.createPresignedPutUrl(objectKey, in.getContentType(), null));
        out.setObjectUrl(objectStorageService.getObjectUrl(objectKey));
        return out;
    }

    @Override
    public SystemFilePresignedDownloadUrlOut getPresignedDownloadUrl(SystemFilePresignedDownloadUrlIn in) {
        String objectKey = extractObjectKey(in.getObjectUrl());
        SystemFilePresignedDownloadUrlOut out = new SystemFilePresignedDownloadUrlOut();
        out.setDownloadUrl(objectStorageService.createPresignedGetUrl(objectKey, null));
        return out;
    }

    private String buildObjectKey(String pathPrefix, String fileName) {
        String normalizedPrefix = normalizePrefix(pathPrefix);
        String suffix = extractFileSuffix(fileName);
        LocalDate now = LocalDate.now();
        String datePath = now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth();
        String randomName = UUID.randomUUID().toString().replace("-", "");
        return normalizedPrefix + "/" + datePath + "/" + randomName + suffix;
    }

    private String normalizePrefix(String pathPrefix) {
        String value = StringUtils.hasText(pathPrefix) ? pathPrefix.trim() : DEFAULT_PATH_PREFIX;
        value = value.replace("\\", "/");
        while (value.startsWith("/")) {
            value = value.substring(1);
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return StringUtils.hasText(value) ? value : DEFAULT_PATH_PREFIX;
    }

    private String extractFileSuffix(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }

    private String extractObjectKey(String objectUrl) {
        String value = objectUrl.trim();
        if (value.startsWith("http://") || value.startsWith("https://")) {
            URI uri = URI.create(value);
            String path = uri.getPath();
            if (!StringUtils.hasText(path)) {
                return "";
            }
            return path.startsWith("/") ? path.substring(1) : path;
        }
        return value.startsWith("/") ? value.substring(1) : value;
    }
}
