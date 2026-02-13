package com.followba.store.objectstorage.config;

import com.followba.store.common.exception.BizException;
import com.followba.store.objectstorage.constant.ObjectStorageConstants;
import com.followba.store.objectstorage.provider.s3.S3ObjectStorageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(ObjectStorageProperties.class)
@ConditionalOnProperty(prefix = "i-tool.oss", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ObjectStorageConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "i-tool.oss", name = "provider", havingValue = "aws-s3", matchIfMissing = true)
    public S3Client s3Client(ObjectStorageProperties properties) {
        ObjectStorageProperties.AwsS3Properties s3 = properties.getAwsS3();
        validateS3Properties(s3);

        var builder = S3Client.builder()
                .region(Region.of(s3.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3.getAccessKey(), s3.getSecretKey())
                ))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(Boolean.TRUE.equals(s3.getPathStyleAccessEnabled()))
                        .build());

        if (StringUtils.hasText(s3.getEndpoint())) {
            builder.endpointOverride(URI.create(s3.getEndpoint()));
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "i-tool.oss", name = "provider", havingValue = "aws-s3", matchIfMissing = true)
    public S3Presigner s3Presigner(ObjectStorageProperties properties) {
        ObjectStorageProperties.AwsS3Properties s3 = properties.getAwsS3();
        validateS3Properties(s3);

        var builder = S3Presigner.builder()
                .region(Region.of(s3.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3.getAccessKey(), s3.getSecretKey())
                ));

        if (StringUtils.hasText(s3.getEndpoint())) {
            builder.endpointOverride(URI.create(s3.getEndpoint()));
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "i-tool.oss", name = "provider", havingValue = "aws-s3", matchIfMissing = true)
    public S3ObjectStorageClient s3ObjectStorageClient(S3Client s3Client,
                                                       S3Presigner s3Presigner,
                                                       ObjectStorageProperties properties) {
        return new S3ObjectStorageClient(s3Client, s3Presigner, properties);
    }

    private void validateS3Properties(ObjectStorageProperties.AwsS3Properties s3) {
        if (!StringUtils.hasText(s3.getRegion())
                || !StringUtils.hasText(s3.getAccessKey())
                || !StringUtils.hasText(s3.getSecretKey())
                || !StringUtils.hasText(s3.getBucket())) {
            throw new BizException(ObjectStorageConstants.S3_CONFIG_INVALID);
        }
    }
}
