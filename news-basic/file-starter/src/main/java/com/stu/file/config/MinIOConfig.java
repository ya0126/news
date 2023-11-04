package com.stu.file.config;

import com.stu.file.service.FileStorageService;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置
 *
 * @EnableConfigurationProperties：开启MinIOConfigProperties.class这个配置类，只有这样MinIOConfigProperties.class这个配置类才会生效。 当然，直接在MinIOConfigProperties.class配置类上加上@component注解也可以
 * <p>
 * 但是不使用component，和下面这个结合使用可以让存在FileStorageService的类才开启这两个注解
 * @ConditionalOnClass：这个注解的意思是，只有存在file...的类MinioConfig配置类才会生效
 */
@Data
@Configuration
@EnableConfigurationProperties(MinIOConfigProperties.class)
@ConditionalOnClass(FileStorageService.class)
public class MinIOConfig {
    @Autowired
    MinIOConfigProperties minIOConfigProperties;

    @Bean
    public MinioClient buildMinioClient() {
        return MinioClient.builder()
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndpoint())
                .build();
    }
}
