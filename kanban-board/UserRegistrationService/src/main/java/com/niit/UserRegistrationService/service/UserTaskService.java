package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.UserAlreadyExistsException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;

import java.util.List;

public interface UserTaskService {
    User registerUser(User user) throws UserAlreadyExistsException;
    User findByUserEmailId(String userEmailId) throws UserNotFoundException;
    List<User> getAllUserData(String userId) throws UserNotFoundException;
    List<User> getUsersByMemberRole(); // Add this method
}
