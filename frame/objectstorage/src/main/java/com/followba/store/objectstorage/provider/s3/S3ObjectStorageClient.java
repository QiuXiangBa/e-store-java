package com.followba.store.objectstorage.provider.s3;

import com.followba.store.common.exception.BizException;
import com.followba.store.objectstorage.config.ObjectStorageProperties;
import com.followba.store.objectstorage.constant.ObjectStorageConstants;
import com.followba.store.objectstorage.core.ObjectStorageClient;
import com.followba.store.objectstorage.core.ObjectStorageProviderType;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

public class S3ObjectStorageClient implements ObjectStorageClient {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final ObjectStorageProperties properties;

    public S3ObjectStorageClient(S3Client s3Client,
                                 S3Presigner s3Presigner,
                                 ObjectStorageProperties properties) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.properties = properties;
    }

    @Override
    public ObjectStorageProviderType providerType() {
        return ObjectStorageProviderType.AWS_S3;
    }

    @Override
    public String upload(String objectKey, byte[] content, String contentType) {
        try {
            if (content == null || content.length == 0) {
                throw new BizException(ObjectStorageConstants.UPLOAD_FAILED, "content 不能为空");
            }
            String normalizedObjectKey = normalizeObjectKey(objectKey);
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(properties.getAwsS3().getBucket())
                            .key(normalizedObjectKey)
                            .contentType(StringUtils.hasText(contentType) ? contentType : DEFAULT_CONTENT_TYPE)
                            .build(),
                    RequestBody.fromBytes(content));
            return getObjectUrl(normalizedObjectKey);
        } catch (Exception e) {
            throw new BizException(ObjectStorageConstants.UPLOAD_FAILED, e.getMessage());
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            String normalizedObjectKey = normalizeObjectKey(objectKey);
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(properties.getAwsS3().getBucket())
                    .key(normalizedObjectKey)
                    .build());
        } catch (Exception e) {
            throw new BizException(ObjectStorageConstants.DELETE_FAILED, e.getMessage());
        }
    }

    @Override
    public String getObjectUrl(String objectKey) {
        String normalizedObjectKey = normalizeObjectKey(objectKey);
        String publicUrlPrefix = properties.getAwsS3().getPublicUrlPrefix();
        if (StringUtils.hasText(publicUrlPrefix)) {
            return String.format("%s/%s", trimSlash(publicUrlPrefix), normalizedObjectKey);
        }
        return s3Client.utilities().getUrl(builder -> builder
                .bucket(properties.getAwsS3().getBucket())
                .key(normalizedObjectKey)).toString();
    }

    @Override
    public String createPresignedPutUrl(String objectKey, String contentType, Duration duration) {
        try {
            String normalizedObjectKey = normalizeObjectKey(objectKey);
            Duration signatureDuration = duration != null ? duration : properties.getAwsS3().getPresignTtl();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(properties.getAwsS3().getBucket())
                    .key(normalizedObjectKey)
                    .contentType(StringUtils.hasText(contentType) ? contentType : DEFAULT_CONTENT_TYPE)
                    .build();
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(signatureDuration)
                    .putObjectRequest(putObjectRequest)
                    .build();
            return s3Presigner.presignPutObject(presignRequest).url().toString();
        } catch (Exception e) {
            throw new BizException(ObjectStorageConstants.PRESIGN_FAILED, e.getMessage());
        }
    }

    @Override
    public String createPresignedGetUrl(String objectKey, Duration duration) {
        try {
            String normalizedObjectKey = normalizeObjectKey(objectKey);
            Duration signatureDuration = duration != null ? duration : properties.getAwsS3().getPresignTtl();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(properties.getAwsS3().getBucket())
                    .key(normalizedObjectKey)
                    .build();
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(signatureDuration)
                    .getObjectRequest(getObjectRequest)
                    .build();
            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            throw new BizException(ObjectStorageConstants.PRESIGN_FAILED, e.getMessage());
        }
    }

    private String normalizeObjectKey(String objectKey) {
        if (!StringUtils.hasText(objectKey)) {
            throw new BizException(ObjectStorageConstants.OBJECT_KEY_INVALID, "objectKey 不能为空");
        }
        return objectKey.startsWith("/") ? objectKey.substring(1) : objectKey;
    }

    private String trimSlash(String url) {
        if (!StringUtils.hasText(url)) {
            return "";
        }
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
