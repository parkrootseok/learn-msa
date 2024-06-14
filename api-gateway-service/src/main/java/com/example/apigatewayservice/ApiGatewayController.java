package com.example.apigatewayservice;

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

    @GetMapping("/health-check")
    public String status() {

        StringBuilder sb = new StringBuilder();
        sb.append("It's working in api gateway service. </br>");
        sb.append("PORT(local.server.port): " + env.getProperty("local.server.port")).append("</br>");
        sb.append("PORT(server.port): " + env.getProperty("server.port")).append("</br>");
        sb.append("token secret: " + env.getProperty("jwt.secret")).append("</br>");
        sb.append("access token expiration time: " + env.getProperty("jwt.expiration.access")).append("</br>");
        sb.append("refresh token expiration time: " + env.getProperty("jwt.expiration.refresh")).append("</br>");
        sb.append("api gateway: " + env.getProperty("gateway.ip")).append("</br>");

        return sb.toString();

    }

}
