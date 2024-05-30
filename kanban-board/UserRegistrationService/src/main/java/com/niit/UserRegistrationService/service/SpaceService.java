package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.Space;
import com.niit.UserRegistrationService.exception.DuplicateSpaceException;
import com.niit.UserRegistrationService.exception.SpaceNotFoundException;
import com.niit.UserRegistrationService.exception.UserNotFoundException;

import java.util.List;

public interface SpaceService {
    Space createSpace(Space space, String emailId) throws UserNotFoundException, DuplicateSpaceException;
    Space updateSpace(String spaceId, Space updatedSpace, String emailId) throws SpaceNotFoundException, UserNotFoundException;
    Space deleteSpace(String spaceId, String emailId) throws SpaceNotFoundException, UserNotFoundException;
    List<Space> getSpacesByEmailId(String emailId) throws UserNotFoundException, SpaceNotFoundException;
    List<Space> getAllSpaces(); // Add this method to retrieve all spaces
}
