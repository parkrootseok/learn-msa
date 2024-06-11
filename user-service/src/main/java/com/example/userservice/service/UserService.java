package com.example.userservice.service;

import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getAllUser();

}
