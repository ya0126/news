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
 * @author yaoh
 */
@Data
@Configuration
// 开启minio...这个配置类，只有这样minio...这个配置类才会生效。当然，直接在minio...配置类上加上@component注解也可以
@EnableConfigurationProperties(MinIOConfigProperties.class)
// 这个注解的意思是，只有存在file...的类MinioConfig配置类才会生效
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
