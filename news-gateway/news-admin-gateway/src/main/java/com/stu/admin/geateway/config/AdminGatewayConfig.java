package com.stu.admin.geateway.config;

import com.stu.admin.geateway.handle.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * admin网关配置类
 *
 * @author yaoh
 */
@Configuration
public class AdminGatewayConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelExceptionHandler() {
        return new SentinelFallbackHandler();
    }
}
