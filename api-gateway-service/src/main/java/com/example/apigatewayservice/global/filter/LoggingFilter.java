package com.example.apigatewayservice.global.filter;

import com.example.apigatewayservice.global.filter.LoggingFilter.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<Config> {

    @Data
    public static class Config {
        public String baseMessage;
        public boolean preLogger;
        public boolean postLogger;
    }

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        /**
         * [ OrderedGatewayFilter ]
         * - 필터의 우선순위를 지정하기 위한 구현체
         */
        GatewayFilter filter =  new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter Message: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter: request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
            }));

        }, Ordered.LOWEST_PRECEDENCE);

        return filter;

    }


}
