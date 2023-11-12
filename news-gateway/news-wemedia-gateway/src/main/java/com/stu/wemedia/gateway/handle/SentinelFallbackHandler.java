package com.stu.wemedia.gateway.handle;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.stu.model.common.dtos.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 自定义限流异常处理
 *
 * @author yaoh
 */
public class SentinelFallbackHandler implements WebExceptionHandler {
    private Mono<Void> writeResponse(ServerResponse serverResponse, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseResult result = ResponseResult.errorResult(500, "请求超过最大数，请稍候再试");
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(throwable);
        }
        if (!BlockException.isBlockException(throwable)) {
            return Mono.error(throwable);
        }
        return handleBlockedRequest(exchange, throwable).flatMap(response -> writeResponse(response, exchange));
    }

    /**
     * 处理请求
     *
     * @param exchange
     * @param throwable
     * @return Mono<ServerResponse>
     */
    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
