package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.Enum.Role;
import com.niit.UserRegistrationService.domain.Space;
import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.UserAlreadyExistsException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.repository.UserTaskRepository;
import com.niit.UserRegistrationService.proxy.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTaskServiceImpl implements UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final UserProxy userProxy;

    @Autowired
    public UserTaskServiceImpl(UserTaskRepository userTaskRepository, UserProxy userProxy) {
        this.userTaskRepository = userTaskRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userTaskRepository.findByEmailId(user.getEmailId()) != null) {
            throw new UserAlreadyExistsException();
        }

        // Create a default space for the user
        Space defaultSpace = createDefaultSpace(user);

        // Add the default space to the user's list of spaces
        List<Space> userSpaces = new ArrayList<>();
        userSpaces.add(defaultSpace);
        user.setSpaces(userSpaces);

        // Save the user to the database
        User savedUser = userTaskRepository.save(user);
        if (!(savedUser.getEmailId().isEmpty())) {
            ResponseEntity<?> r = userProxy.saveUser(user);
            System.out.println(r.getBody());
        }
        return savedUser;
    }

    // Method to create a default space for the user
    private Space createDefaultSpace(User user) {
        // You can customize this method to create a default space for each user
        // For demonstration purposes, let's create a space with the user's email ID as the name
        return new Space();
    }

    @Override
    public User findByUserEmailId(String userEmailId) throws UserNotFoundException {
        return userTaskRepository.findByEmailId(userEmailId);
    }

    @Override
    public List<User> getAllUserData(String userId) throws UserNotFoundException {
        User user = userTaskRepository.findByEmailId(userId);
        if (user != null) {
            return userTaskRepository.findAll();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getUsersByMemberRole() {
        return userTaskRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.MEMBER)
                .collect(Collectors.toList());
    }
}
