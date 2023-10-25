package com.heima.admin.geateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 后台管理系统-全局认证过滤器
 *
 * @author yaoh
 */
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object token = exchange.getAttribute("token");
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
