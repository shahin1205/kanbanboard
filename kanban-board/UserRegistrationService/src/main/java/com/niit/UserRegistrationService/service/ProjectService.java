package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.Project;
import com.niit.UserRegistrationService.exception.ProjectNotFoundException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, String spaceId, String userId) throws SpaceNotFoundException, UserNotFoundException;
    Project updateProject(String projectId, String projectName, String userId) throws ProjectNotFoundException, UserNotFoundException;
    void deleteProject(String projectId, String userId) throws ProjectNotFoundException, UserNotFoundException;
    List<Project> getAllProjects(String spaceId) throws SpaceNotFoundException;
    void assignProjectToUserByEmail(String userEmailId, String projectId) throws UserNotFoundException, ProjectNotFoundException;


}
