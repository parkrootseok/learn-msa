package com.example.userservice.domain.service;

import com.example.userservice.domain.model.dto.UserDto;
import com.example.userservice.domain.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getAllUser();

    UserDto getUserDtoByEmail(String email);

}
