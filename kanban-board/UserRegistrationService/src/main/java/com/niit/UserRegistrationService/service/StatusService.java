package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.Status;
import com.niit.UserRegistrationService.exception.StatusNotFoundException;

import java.util.List;

public interface StatusService {
    Status createStatus(Status status);
    Status updateStatus(String statusCode, Status status) throws StatusNotFoundException;
    Status getStatusByCode(String statusCode) throws StatusNotFoundException;
    List<Status> getAllStatuses();
    void deleteStatus(String statusCode) throws StatusNotFoundException;
}
