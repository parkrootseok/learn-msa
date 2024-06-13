package com.example.apigatewayservice.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    public boolean isValid(String accessToken) {

        boolean isValid = true;
        String subject = null;

        try {

            subject = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(env.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload()
                    .getSubject();

        } catch (Exception e) {
            isValid = false;
        }

        if (Objects.isNull(subject) || subject.isEmpty()) {
            isValid = false;
        }

        return isValid;

    }

}
