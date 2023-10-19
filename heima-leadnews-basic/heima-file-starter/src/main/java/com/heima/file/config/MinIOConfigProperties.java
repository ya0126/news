package com.heima.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 读取minio配置文件
 *
 * @author yaoh
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOConfigProperties implements Serializable {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    private String readPath;
}
