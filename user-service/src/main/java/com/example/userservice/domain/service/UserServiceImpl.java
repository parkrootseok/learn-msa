package com.example.userservice.domain.service;

import com.example.userservice.domain.exception.NotExistsUserException;
import com.example.userservice.domain.model.dto.UserDto;
import com.example.userservice.domain.model.entity.UserEntity;
import com.example.userservice.domain.model.response.GetOrderResponse;
import com.example.userservice.domain.repository.UserRepository;
import com.example.userservice.global.error.ErrorCode;
import com.example.userservice.infra.openfeign.client.OrderServiceClient;
import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
    public UserDto getUserByUserId(String userId) throws FeignException {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (Objects.isNull(userEntity)) {
            throw new NotExistsUserException(ErrorCode.NOT_EXISTS_USER);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        /**
         * RestTemplate 방식
         */
//        ResponseEntity<List<GetOrderResponse>> response = restTemplate
//                .exchange(
//                        String.format(env.getProperty("order-service.url.get-orders"), userId),
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<GetOrderResponse>>() {}
//                );
//        List<GetOrderResponse> orders = response.getBody();

        /**
         * OpenFeign 방식
         */
//        List<GetOrderResponse> orders = orderServiceClient.getOrders(userId);

        /**
         * Circuitbreaker를 활용하여 호출
         */
        log.info("Before call orders mircroservice");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuit-breaker");
        List<GetOrderResponse> orders = circuitBreaker.run(
                () -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>()
        );
        log.info("After call orders mircroservice");

        userDto.setOrders(orders);

        return userDto;

    }

    @Override
    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = userRepository.findByEmail(email);

        if (Objects.isNull(userEntity)) {
            throw new NotExistsUserException(ErrorCode.NOT_EXISTS_USER);
        }

        return mapper.map(userEntity, UserDto.class);

    }

}
