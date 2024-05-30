package com.niit.UserAuthentication.repository;

import com.niit.UserAuthentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailIdAndPassword(String emailId, String password);
}
