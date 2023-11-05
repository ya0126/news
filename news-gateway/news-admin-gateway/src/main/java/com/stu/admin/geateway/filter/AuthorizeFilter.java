package com.stu.admin.geateway.filter;

import com.stu.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 后台管理系统-全局认证过滤器
 *
 * @author yaoh
 */
@Slf4j
@Component
public class AuthorizeFilter implements Ordered, GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (request.getURI().getPath().contains("login")) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        try {
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            int result = JwtUtil.verifyToken(claimsBody);
            if (result == 1 || result == 2) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            } else {
                Integer userId = (Integer) JwtUtil.getClaimsBody(token).get("user_id");
                addHeader(request.mutate(), "user_id", String.valueOf(userId));
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 添加请求头
     *
     * @param mutate
     * @param name
     * @param value
     */
    protected void addHeader(ServerHttpRequest.Builder mutate, String name, String value) {
        if (value == null) return;
        mutate.header(name, value);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
