package com.stu.app.gateway.config;

import com.stu.app.gateway.handle.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * app网关配置类
 *
 * @author yaoh
 */
@Configuration
public class AppGatewayConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelExceptionHandler() {
        return new SentinelFallbackHandler();
    }
}
