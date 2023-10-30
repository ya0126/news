package com.heima.admin.geateway.filter;

import com.heima.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
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
@Order(0)
@Component
public class AuthorizeFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        boolean login = request.getURI().getPath().contains("login");
        if (login) {
            log.info("登录放行");
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setComplete();
            log.info("未携带token，结束请求");
        }

        try {
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            int result = JwtUtil.verifyToken(claimsBody);
            if (result == 1 || result == 2) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            log.info("token无效，结束请求");
        } catch (Exception e) {
            log.error("token校验异常", e);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setComplete();
        }

        log.info("token有效，放行");
        return chain.filter(exchange);
    }
}
