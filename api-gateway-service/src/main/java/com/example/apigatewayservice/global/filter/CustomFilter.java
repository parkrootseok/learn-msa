package com.example.apigatewayservice.global.filter;

import com.example.apigatewayservice.global.filter.CustomFilter.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<Config> {

    public static class Config {

    }

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        /**
         * [ exchange ]
         * - 필요한 Request, Response를 얻기위한 객체
         */
        // Custom Pre Filter
        return (exchange, chain) -> {

          ServerHttpRequest request = exchange.getRequest();
          ServerHttpResponse response = exchange.getResponse();

          log.info("Custom PRE Filter: request id -> {}", request.getId());

          // Custom Post Filter
          return chain.filter(exchange)
                  .then(Mono.fromRunnable(() -> {
                      log.info("Custom POST Filter: response code -> {}", response.getStatusCode());
                  }));

        };

    }

}
