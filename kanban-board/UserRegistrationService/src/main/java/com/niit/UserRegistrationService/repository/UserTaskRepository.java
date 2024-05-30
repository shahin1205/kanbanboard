package com.niit.UserRegistrationService.repository;

import com.niit.UserRegistrationService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTaskRepository extends MongoRepository<User, String> {
    User findByEmailId(String emailId);
}
