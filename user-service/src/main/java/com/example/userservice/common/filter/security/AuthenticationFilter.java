package com.example.userservice.common.filter.security;

import com.example.userservice.common.util.JwtUtil;
import com.example.userservice.domain.model.dto.UserDto;
import com.example.userservice.domain.model.request.LoginUserRequest;
import com.example.userservice.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private JwtUtil jwtUtil;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil
    ) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * [ attemptAuthentication ]
     * - 사용자의 요청에 대한 인증 작업을 수행
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        try {

            LoginUserRequest credential = new ObjectMapper().readValue(request.getInputStream(), LoginUserRequest.class);

            // 인자로 받은 정보를 참조하여 인증 작업 수행
            return getAuthenticationManager().authenticate(
                    // 사용자가 입력한 인증 정보를 Security에서 사용할 수 있도록 변환 후 전달
                    new UsernamePasswordAuthenticationToken(
                            credential.getEmail(),
                            credential.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * [ successfulAuthentication ]
     * - 사용자에 대한 인증이 성공한 후 처리 동작을 정의 ex) 토큰 생성 등등
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {

        String email = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDtoByEmail(email);

        String accessToken = jwtUtil.issueAccessToken(userDto.getUserId());
        response.addHeader("Access-Token", accessToken);

    }

}
