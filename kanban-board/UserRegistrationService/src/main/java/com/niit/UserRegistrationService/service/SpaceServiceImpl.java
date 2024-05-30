package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.Enum.Role;
import com.niit.UserRegistrationService.domain.Space;
import com.niit.UserRegistrationService.domain.User;
import com.niit.UserRegistrationService.exception.DuplicateSpaceException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;
import com.niit.UserRegistrationService.repository.SpaceRepository;
import com.niit.UserRegistrationService.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final UserTaskRepository userRepository;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository, UserTaskRepository userRepository) {
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Space createSpace(Space space, String emailId) throws UserNotFoundException, DuplicateSpaceException {
        User user = userRepository.findByEmailId(emailId);
        if (user == null || user.getRole() != Role.TEAM_LEAD) {
            throw new UserNotFoundException();
        }

        // Check if a space with the same name already exists
        if (spaceRepository.existsBySpaceName(space.getSpaceName())) {
            throw new DuplicateSpaceException("Space with the same name already exists");
        }

        Space createdSpace = spaceRepository.save(space);
        user.addSpace(createdSpace);
        userRepository.save(user);
        return createdSpace;
    }

    @Override
    public List<Space> getSpacesByEmailId(String emailId) throws UserNotFoundException, SpaceNotFoundException {
        User user = userRepository.findByEmailId(emailId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        List<Space> userSpaces = user.getSpaces();
        if (userSpaces.isEmpty()) {
            throw new SpaceNotFoundException();
        }
        return userSpaces;
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @Override
    public Space updateSpace(String spaceId, Space updatedSpace, String emailId) throws SpaceNotFoundException, UserNotFoundException {
        User user = userRepository.findByEmailId(emailId);
        if (user == null || user.getRole() != Role.TEAM_LEAD) {
            throw new UserNotFoundException();
        }

        Optional<Space> spaceOptional = spaceRepository.findById(spaceId);
        if (spaceOptional.isEmpty()) {
            throw new SpaceNotFoundException();
        }

        Space existingSpace = spaceOptional.get();
        existingSpace.setSpaceName(updatedSpace.getSpaceName());
        existingSpace.setProjects(updatedSpace.getProjects());
        return spaceRepository.save(existingSpace);
    }

    @Override
    public Space deleteSpace(String spaceId, String emailId) throws SpaceNotFoundException, UserNotFoundException {
        User user = userRepository.findByEmailId(emailId);
        if (user == null || user.getRole() != Role.TEAM_LEAD) {
            throw new UserNotFoundException();
        }

        Optional<Space> spaceOptional = spaceRepository.findById(spaceId);
        if (spaceOptional.isEmpty()) {
            throw new SpaceNotFoundException();
        }

        Space space = spaceOptional.get();
        spaceRepository.delete(space);
        user.getSpaces().remove(space);
        userRepository.save(user);
        return space;
    }
}
