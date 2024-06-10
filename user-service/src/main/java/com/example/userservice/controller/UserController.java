package com.example.userservice.controller;

import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.model.request.CreateUserRequest;
import com.example.userservice.model.response.CreateUserResponse;
import com.example.userservice.model.response.GetUserResponse;
import com.example.userservice.model.vo.GreetingVO;
import com.example.userservice.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user-service")
public class UserController {

    private final Environment env;
    private final GreetingVO greetingVO;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(Environment env, GreetingVO greetingVO, UserService userService, ModelMapper mapper) {
        this.env = env;
        this.greetingVO = greetingVO;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/health-check")
    private String status() {

        return String.format("It's working in user service on PORT %s", env.getProperty("local.server.port"));

    }

    @GetMapping("/welcome")
    public String welcome() {

        return greetingVO.getMessage();

    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {

        UserDto createUserDto = userService.createUser(mapper.map(createUserRequest, UserDto.class));
        CreateUserResponse createUserResponse = mapper.map(createUserDto, CreateUserResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createUserResponse);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("userId") String userId) {

        UserDto user = userService.getUserByUserId(userId);
        GetUserResponse getUserResponse = mapper.map(user, GetUserResponse.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getUserResponse);

    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getUsers() {

        Iterable<User> users = userService.getAllUser();
        List<GetUserResponse> getUserResponses = new ArrayList<>();
        users.forEach(
                user -> getUserResponses.add(mapper.map(user, GetUserResponse.class))
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getUserResponses);

    }

}
