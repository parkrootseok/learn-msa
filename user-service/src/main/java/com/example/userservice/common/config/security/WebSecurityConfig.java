package com.example.userservice.common.config.security;

import com.example.userservice.common.filter.security.AuthenticationFilter;
import com.example.userservice.common.util.JwtUtil;
import com.example.userservice.domain.service.UserService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    public static final String ALLOWED_IP_ADDRESS = "192.168.1.15";
    public static final String SUBNET = "/32";
    public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

    private static final String[] WHITE_LIST = {
            "/h2-console/**",
            "/health-check/**"
    };

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Spring Security를 적용하지 않을 URI 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web.ignoring()
                        .requestMatchers(WHITE_LIST)
                        .requestMatchers(new AntPathRequestMatcher("/users", "POST"));
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        return httpSecurity

                // CSRF 비활성화 (RESTful 방식은 무상태성을 가지므로 불필요)
                .csrf(AbstractHttpConfigurer::disable)

                // H2 DB 사용을 위해 추가
                .headers(header ->
                        header.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                // 허용 IP 설정
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/**").access(this::hasIpAddress)
                                .anyRequest()
                                .authenticated()
                )

                // 필터 등록
                .addFilter(getAuthenticationFilter(authenticationManager))

                .build();

    }

    /**
     * [ AuthenticationManager ]
     * - AuthenticationConfiguration를 통해 Bean 등록
     * - userDetailService, PasswordEncoder는 Spring Security 6부터 자동 주입
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        return new AuthorizationDecision(ALLOWED_IP_ADDRESS_MATCHER.matches(context.getRequest()));
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, jwtUtil);
    }

}
