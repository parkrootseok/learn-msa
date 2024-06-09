package com.example.userservice.config.security;

import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST = {
            "/h2-console/**",
            "/users/**",
            "/actuator/**"
    };

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final Environment env;
//    private final UserService userService;
//
//    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, Environment env, UserService userService) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.env = env;
//        this.userService = userService;
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity

                // CSRF 비활성화 (RESTful 방식은 무상태성을 가지므로 불필요)
                .csrf(AbstractHttpConfigurer::disable)

                // H2 DB 사용을 위해 추가
                .headers(
                        header -> header.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .authorizeHttpRequests(
                        (auth -> auth.requestMatchers(WHITE_LIST).permitAll())
                )

                .build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
