package com.stu.wemedia.gateway.config;

import com.stu.wemedia.gateway.handle.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 自媒体网关配置类
 *
 * @author yaoh
 */
@Configuration
public class WemediaGatewayConfig {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelFallbackHandler() {
        return new SentinelFallbackHandler();
    }
}
