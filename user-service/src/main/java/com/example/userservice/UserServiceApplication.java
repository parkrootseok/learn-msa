package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  [ @EnableDiscoveryClient ]
 *  - service discovery 서버에 인스턴스로 등록하기 위한 어노테이션
 *  - 'spring-cloud-starter-netflix-eureka-server' 의존성을 추가한 경우 어노테이션을 사용하지 않아도 자동 등록
 */
@EnableDiscoveryClient

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
