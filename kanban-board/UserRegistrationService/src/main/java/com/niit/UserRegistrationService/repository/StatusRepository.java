package com.niit.UserRegistrationService.repository;

import com.niit.UserRegistrationService.domain.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status, String> {
}
