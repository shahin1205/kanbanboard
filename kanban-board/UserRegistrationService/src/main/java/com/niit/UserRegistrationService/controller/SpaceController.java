package com.niit.UserRegistrationService.controller;

import com.niit.UserRegistrationService.domain.Space;
import com.niit.UserRegistrationService.exception.DuplicateSpaceException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.service.SpaceService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public ResponseEntity<?> createSpace(@RequestBody Space space, HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            Space createdSpace = spaceService.createSpace(space, email);
            return new ResponseEntity<>(createdSpace, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found or unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (DuplicateSpaceException e) {
            return new ResponseEntity<>("Space with the same name already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getSpacesByEmail")
    public ResponseEntity<?> getSpacesByEmail(HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            List<Space> spaces = spaceService.getSpacesByEmailId(email);
            return new ResponseEntity<>(spaces, HttpStatus.OK);
        } catch (UserNotFoundException | SpaceNotFoundException e) {
            return new ResponseEntity<>("Spaces not found for the user", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllSpaces")
    public ResponseEntity<?> getAllSpaces() {
        try {
            List<Space> spaces = spaceService.getAllSpaces();
            return new ResponseEntity<>(spaces, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving spaces", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<?> deleteSpace(@PathVariable String spaceId, HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            spaceService.deleteSpace(spaceId, email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SpaceNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>("Space not found or unauthorized", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = Jwts.parser().setSigningKey("mysecret").parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
