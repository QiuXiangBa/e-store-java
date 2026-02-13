# objectstorage 模块说明

## 模块定位

`objectstorage` 是统一的对象存储抽象层，负责屏蔽不同云厂商 SDK 差异。

当前已支持：
- AWS S3（provider = `aws-s3`）

## 抽象层设计

- `ObjectStorageClient`：厂商能力接口（上传、删除、获取 URL、签名 URL）
- `ObjectStorageProviderType`：服务商枚举
- `ObjectStorageClientFactory`：按 provider 路由具体实现
- `ObjectStorageService`：默认门面服务，对业务层暴露统一调用
- `ObjectStorageProperties`：统一配置入口

## 扩展新云厂商步骤

1. 在 `ObjectStorageProviderType` 增加新 provider code
2. 新增实现类（如 `OssObjectStorageClient`）实现 `ObjectStorageClient`
3. 在 `ObjectStorageProperties` 中增加该厂商配置项
4. 在 `ObjectStorageConfiguration` 中新增对应 Bean

业务调用层无需改动，只需调整配置 `i-tool.oss.provider`。

## 配置示例

```yaml
store:
  object-storage:
    enabled: true
    provider: aws-s3
    aws-s3:
      endpoint: https://s3.us-east-1.amazonaws.com
      region: us-east-1
      access-key: xxx
      secret-key: xxx
      bucket: your-bucket
      public-url-prefix: https://cdn.your-domain.com
      path-style-access-enabled: false
      presign-ttl: 30m
```

## 接入方式

在应用启动类引入：

```java
@ImportObjectStorage
@SpringBootApplication
public class XxxApplication {
}
```

并添加模块依赖：

```xml
<dependency>
  <groupId>com.followba.store</groupId>
  <artifactId>objectstorage</artifactId>
</dependency>
```
