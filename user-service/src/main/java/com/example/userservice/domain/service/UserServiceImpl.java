package com.example.userservice.domain.service;

import com.example.userservice.common.constants.error.ErrorCode;
import com.example.userservice.domain.exception.NotExistsUserException;
import com.example.userservice.domain.model.dto.UserDto;
import com.example.userservice.domain.model.entity.UserEntity;
import com.example.userservice.domain.model.response.GetOrderResponse;
import com.example.userservice.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (Objects.isNull(userEntity)) {
            throw new NotExistsUserException(ErrorCode.NOT_EXISTS_USER);
        }

        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );

    }

    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);

    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity =  userRepository.findByUserId(userId);

        if (Objects.isNull(userEntity)) {
            throw new NotExistsUserException(ErrorCode.NOT_EXISTS_USER);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        List<GetOrderResponse> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;

    }

    @Override
    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

}
