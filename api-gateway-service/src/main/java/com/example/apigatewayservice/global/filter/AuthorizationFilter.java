package com.example.apigatewayservice.global.filter;

import com.example.apigatewayservice.global.constants.error.ErrorCode;
import com.example.apigatewayservice.global.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    public static class Config {

    }

    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 헤더에 AUTHORIZATION 정보가 포함되어 있는지 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, ErrorCode.NOT_CONTAIN_AUTHORIZATION);
            }

            // token 가져오기
            String accessToken = request.getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0)
                    .replace(TOKEN_PREFIX, "");

            if (!jwtUtil.isValid(accessToken)) {
                return onError(exchange, ErrorCode.INVALID_TOKEN);
            }

            return chain.filter(exchange);
        });

    }

    private Mono<Void> onError(ServerWebExchange exchange, ErrorCode errorCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorCode.getHttpStatus());
        log.error(errorCode.getMessage());
        return response.setComplete();
    }

}
