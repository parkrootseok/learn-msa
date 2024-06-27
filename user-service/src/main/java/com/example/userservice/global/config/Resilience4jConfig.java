package com.example.userservice.global.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerCustomizer() {

        CircuitBreakerConfig circuitBreakerConfig =
                CircuitBreakerConfig.custom()
                        /* CircuitBreaker 실행 임계점 설정 (default : 50, 100번중 50번 실패할 경우 수행) */
                        .failureRateThreshold(4)
                        /* CircuitBreaker 실행 지속 시간 설정 (default : 60, 설정한 시간 이후 half-open) */
                        .waitDurationInOpenState(Duration.ofSeconds(1))
                        /* CircuitBreaker 결과 기록에 사용되는 유형 설정 (카운트 or 시간) */
                        .slidingWindowType(SlidingWindowType.COUNT_BASED)
                        /* CircuitBreaker 결과 기록에 사용되는 슬라이딩 크기 설정 (default : 100) */
                        .slidingWindowSize(2)
                        .build();

        TimeLimiterConfig timeLimiterConfig =
                TimeLimiterConfig.custom()
                        /* supplier(요청받은 mircoservice) time limit 설정 (아래 설정한 시간 만큼 응답이 없다면 CircuitBreaker 작동)  */
                        .timeoutDuration(Duration.ofSeconds(4))
                        .build();

        return factory ->
                factory.configureDefault(id ->
                        new Resilience4JConfigBuilder(id)
                                .timeLimiterConfig(timeLimiterConfig)
                                .circuitBreakerConfig(circuitBreakerConfig)
                                .build()
                );

    }

}
