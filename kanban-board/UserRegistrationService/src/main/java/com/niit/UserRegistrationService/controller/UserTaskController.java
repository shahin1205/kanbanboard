package com.niit.UserRegistrationService.controller;

import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.UserAlreadyExistsException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.service.UserTaskService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserTaskController {

    private UserTaskService userTaskService;
    private ResponseEntity<?> responseEntity;
    @Autowired
    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        // Register a new user and save to db, return 201 status if user is saved else 500 status
        try {
            responseEntity =  new ResponseEntity<>(userTaskService.registerUser(user), HttpStatus.CREATED);
        }
        catch(UserAlreadyExistsException e)
        {
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

    @GetMapping("/{userEmailId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userEmailId) throws UserNotFoundException {
        try {
            User user = userTaskService.findByUserEmailId(userEmailId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/getAllDetails")
    public ResponseEntity<?> getAllDetails(HttpServletRequest request) {
        String userId = extractUserIdFromToken(request);
        try {
            List<User> users = userTaskService.getAllUserData(userId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/members")
    public ResponseEntity<?> getUsersByMemberRole() {
        List<User> members = userTaskService.getUsersByMemberRole();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    private String extractUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = Jwts.parser().setSigningKey("mysecret").parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
