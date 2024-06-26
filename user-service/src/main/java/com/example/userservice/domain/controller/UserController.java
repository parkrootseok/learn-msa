package com.example.userservice.domain.controller;

import com.example.userservice.domain.model.dto.UserDto;
import com.example.userservice.domain.model.entity.UserEntity;
import com.example.userservice.domain.model.request.CreateUserRequest;
import com.example.userservice.domain.model.response.CreateUserResponse;
import com.example.userservice.domain.model.response.GetUserResponse;
import com.example.userservice.domain.service.UserService;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final UserService userService;

    @Timed(value = "users.status", longTask = true)
    @GetMapping("/health-check")
    public String status() {

        return new StringBuilder()
                .append("It's working in user service.").append("</br>")
                .append("active profile: ").append(env.getProperty("profile.name")).append("</br>")
                .append("PORT(local.server.port): ").append(env.getProperty("local.server.port")).append("</br>")
                .append("PORT(server.port): ").append(env.getProperty("server.port")).append("</br>")
                .append("token secret: ").append(env.getProperty("jwt.secret")).append("</br>")
                .append("access token expiration time: ").append(env.getProperty("jwt.expiration.access")).append("</br>")
                .append("refresh token expiration time: ").append(env.getProperty("jwt.expiration.refresh")).append("</br>")
                .append("api gateway: ").append(env.getProperty("gateway.ip")).append("</br>")
                .toString();

    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest createUserRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto createUserDto = userService.createUser(
                mapper.map(createUserRequest, UserDto.class));
        CreateUserResponse createUserResponse = mapper.map(createUserDto, CreateUserResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createUserResponse);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("userId") String userId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto user = userService.getUserByUserId(userId);
        GetUserResponse getUserResponse = mapper.map(user, GetUserResponse.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getUserResponse);

    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getUsers() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<UserEntity> users = userService.getAllUser();
        List<GetUserResponse> response = new ArrayList<>();
        users.forEach(
                userEntity -> response.add(mapper.map(userEntity, GetUserResponse.class))
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

}
