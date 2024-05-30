package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.Enum.Role;
import com.niit.UserRegistrationService.domain.Project;
import com.niit.UserRegistrationService.domain.Space;
import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.ProjectNotFoundException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.repository.ProjectRepository;
import com.niit.UserRegistrationService.repository.SpaceRepository;
import com.niit.UserRegistrationService.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserTaskRepository userRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public Project createProject(Project project, String spaceId, String userId) throws SpaceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.TEAM_LEAD) {
            throw new RuntimeException("Only Team Leads can create projects.");
        }

        // Fetch the Space by spaceId
        Space space = spaceRepository.findById(spaceId).orElseThrow(SpaceNotFoundException::new);

        // Set the spaceId for the project
        project.setSpaceId(spaceId);
        Project createdProject = projectRepository.save(project);

        // Add the project to the space
        space.getProjects().add(createdProject);
        spaceRepository.save(space);

        return createdProject;
    }


    @Override
    public void assignProjectToUserByEmail(String userEmailId, String projectId) throws UserNotFoundException, ProjectNotFoundException {
        User user = userRepository.findByEmailId(userEmailId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.hasMemberRole()) {
            throw new RuntimeException("Only members can have projects assigned to them");
        }

        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        // Add project to the user's first space
        user.addProject(project);

        userRepository.save(user);
    }




    @Override
    public Project updateProject(String projectId, String projectName, String userId) throws ProjectNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.TEAM_LEAD) {
            throw new RuntimeException("Only Team Leads can update projects.");
        }
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.setProjectName(projectName);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String projectId, String userId) throws ProjectNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.TEAM_LEAD) {
            throw new RuntimeException("Only Team Leads can delete projects.");
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public List<Project> getAllProjects(String spaceId) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new RuntimeException("Space not found"));
        return space.getProjects();
    }
}