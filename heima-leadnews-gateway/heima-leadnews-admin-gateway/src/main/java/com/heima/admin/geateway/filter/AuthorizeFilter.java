package com.heima.admin.geateway.filter;

import com.heima.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 后台管理系统-全局认证过滤器
 *
 * @author yaoh
 */
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 登录放行
        boolean login = request.getURI().getPath().contains("login");
        if (login){
            chain.filter(exchange);
        }

        // 获取token
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setComplete();
        }

        // 校验token
        try {
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            int result = JwtUtil.verifyToken(claimsBody);
            if (result==1||result==2){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setComplete();
        }

        // token有效，放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
