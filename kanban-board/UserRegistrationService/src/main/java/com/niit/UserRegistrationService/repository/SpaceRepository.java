package com.niit.UserRegistrationService.repository;

import com.niit.UserRegistrationService.domain.Space;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpaceRepository extends MongoRepository<Space, String> {
    boolean existsBySpaceName(String spaceName);
}
