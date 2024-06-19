package com.example.userservice;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 *  [ @EnableDiscoveryClient ]
 *  - service discovery 서버에 인스턴스로 등록하기 위한 어노테이션
 *  - 'spring-cloud-starter-netflix-eureka-server' 의존성을 추가한 경우 어노테이션을 사용하지 않아도 자동 등록
 */
@EnableDiscoveryClient
/**
 *  [ @EnableFeignClient ]
 *  - OpenFeign을 사용하기 위한 어노테이션
 */
@EnableFeignClients
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * [ @LoadBalanced ]
	 * - IP 주소를 사용하지 않고 유레카에 등록된 마이크로 서비스의 이름을 사용하기 위한 어노테아션
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
