package com.followba.store.objectstorage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(
        prefix = "i-tool.oss"
)
public class ObjectStorageProperties {

    private boolean enabled = true;

    private String provider = "aws-s3";

    private AwsS3Properties awsS3 = new AwsS3Properties();

    @Data
    public static class AwsS3Properties {

        private String endpoint;

        private String region;

        private String accessKey;

        private String secretKey;

        private String bucket;

        private String publicUrlPrefix;

        private Boolean pathStyleAccessEnabled = false;

        private Duration presignTtl = Duration.ofMinutes(30);
    }
}
