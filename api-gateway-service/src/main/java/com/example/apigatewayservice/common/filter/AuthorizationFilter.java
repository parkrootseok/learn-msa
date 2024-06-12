package com.example.apigatewayservice.common.filter;

import com.example.apigatewayservice.common.constants.error.ErrorCode;
import com.example.apigatewayservice.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(Environment env, JwtUtil jwtUtil) {
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

            // Authorization 가져오기
            String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String accessToken = authorization.replace("Bearer ", "");

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
