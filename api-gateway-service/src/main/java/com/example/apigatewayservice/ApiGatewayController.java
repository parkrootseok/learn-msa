package com.example.apigatewayservice;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ApiGatewayController {

    private final Environment env;

    @Timed(value = "api-gateway.status", longTask = true)
    @GetMapping("/health-check")
    public String status() {

        return new StringBuilder()
                .append("It's working in api gateway service.").append("</br>")
                .append("active profile: ").append(env.getProperty("profile.name")).append("</br>")
                .append("PORT(local.server.port): ").append(env.getProperty("local.server.port")).append("</br>")
                .append("PORT(server.port): ").append(env.getProperty("server.port")).append("</br>")
                .append("token secret: ").append(env.getProperty("jwt.secret")).append("</br>")
                .append("access token expiration time: ").append(env.getProperty("jwt.expiration.access")).append("</br>")
                .append("refresh token expiration time: ").append(env.getProperty("jwt.expiration.refresh")).append("</br>")
                .append("api gateway: ").append(env.getProperty("gateway.ip")).append("</br>")
                .toString();

    }

}
