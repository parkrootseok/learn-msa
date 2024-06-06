package com.example.apigatewayservice.global.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JAVA code를 사용하여 route 관련 설정 가능
 */
//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        /**
         * Route 설정
         * - path와 매칭되는 엔드포인트에 대한 요청에 filter 추가
         * - URI 매핑
         */
        return builder.routes()
                .route(r -> r.path("/first-service/**")

                        .filters(
                                f -> f
                                        // Request Header에 추가할 정보
                                        .addRequestHeader("first-service", "first-service-request")

                                        // Response Header에 추가할 정보
                                        .addResponseHeader("first-service", "first-service-response")
                        )

                        // 매핑할 uri
                        .uri("http://localhost:8081"))

                .route(r -> r.path("/second-service/**")

                        .filters(
                                f -> f
                                        .addRequestHeader("second-service", "second-service-request")
                                        .addResponseHeader("second-service", "second-service-response")
                        )

                        .uri("http://localhost:8082"))

                .build();

    }

}
