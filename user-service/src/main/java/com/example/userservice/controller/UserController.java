package com.example.userservice.controller;

import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.request.CreateUserRequest;
import com.example.userservice.model.response.CreateUserResponse;
import com.example.userservice.model.vo.GreetingVO;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final GreetingVO greetingVO;
    private final UserService userService;

    @Autowired
    public UserController(Environment env, GreetingVO greetingVO, UserService userService) {
        this.env = env;
        this.greetingVO = greetingVO;
        this.userService = userService;
    }

    @GetMapping("/health-check")
    private String status() {
        return "It's working in user service.";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greetingVO.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto createUserDto = userService.createUser(mapper.map(createUserRequest, UserDto.class));

        CreateUserResponse createUserResponse = mapper.map(createUserDto, CreateUserResponse.class);
        return new ResponseEntity<>(createUserResponse, HttpStatus.CREATED);

    }

}
