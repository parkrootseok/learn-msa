package com.example.userservice.infra.jwt;

import static com.example.userservice.infra.jwt.TokenType.ACCESS_TOKEN;
import static com.example.userservice.infra.jwt.TokenType.REFRESH_TOKEN;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    public String issueAccessToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .claim("token-type", ACCESS_TOKEN.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN.getExpirationTime()))
                .signWith(Keys.hmacShaKeyFor(
                        env.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String issueRefreshToken() {
        return Jwts.builder()
                .claim("token-type", REFRESH_TOKEN.getName())
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + REFRESH_TOKEN.getExpirationTime()))
                .signWith(Keys.hmacShaKeyFor(
                        env.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

}
