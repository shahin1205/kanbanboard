package com.niit.UserRegistrationService.controller;

import com.niit.UserRegistrationService.domain.Project;
import com.niit.UserRegistrationService.exception.ProjectNotFoundException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.service.ProjectService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project, @RequestParam String spaceId, HttpServletRequest request) {
        try {
            String userId = extractUserIdFromToken(request);
            Project createdProject = projectService.createProject(project, spaceId, userId);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (SpaceNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{projectId}/assign")
    public ResponseEntity<?> assignProjectToUser(@RequestParam String userEmailId, @PathVariable String projectId, HttpServletRequest request) {
        try {
            // Assign the project to the user
            projectService.assignProjectToUserByEmail(userEmailId, projectId);

            return new ResponseEntity<>("Project assigned to user successfully", HttpStatus.OK);
        } catch (UserNotFoundException | ProjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{projectId}/name")
    public ResponseEntity<?> updateProjectName(@PathVariable String projectId, @RequestParam String projectName, HttpServletRequest request) {
        try {
            String userId = extractUserIdFromToken(request);
            Project updatedProject = projectService.updateProject(projectId, projectName, userId);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (ProjectNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects(@RequestParam String spaceId) {
        try {
            List<Project> projects = projectService.getAllProjects(spaceId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (SpaceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, HttpServletRequest request) {
        try {
            String userId = extractUserIdFromToken(request);
            projectService.deleteProject(projectId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProjectNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractUserIdFromToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            Claims claims = Jwts.parser().setSigningKey("mysecret").parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (SignatureException | NullPointerException e) {
            throw new RuntimeException("Invalid or missing token");
        }
    }
}
