package com.example.userservice.service;

import com.example.userservice.error.ErrorCode;
import com.example.userservice.exception.NotExistsUserException;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.model.response.GetOrderResponse;
import com.example.userservice.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = mapper.map(userDto, User.class);
        user.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return mapper.map(user, UserDto.class);

    }

    @Override
    public UserDto getUserByUserId(String userId) {

        User user =  userRepository.findByUserId(userId);

        if (Objects.isNull(user)) {
            throw new NotExistsUserException(ErrorCode.NOT_EXISTS_USER);
        }

        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        List<GetOrderResponse> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;

    }

    @Override
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

}
