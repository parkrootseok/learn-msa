package com.example.userservice.repository;

import com.example.userservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserId(String userId);

}
