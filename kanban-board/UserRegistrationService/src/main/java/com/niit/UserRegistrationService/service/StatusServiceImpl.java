package com.niit.UserRegistrationService.service;

import com.niit.UserRegistrationService.domain.Status;
import com.niit.UserRegistrationService.exception.StatusNotFoundException;
import com.niit.UserRegistrationService.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status updateStatus(String statusCode, Status status) throws StatusNotFoundException {
        Status existingStatus = statusRepository.findById(statusCode).orElseThrow(StatusNotFoundException::new);
        existingStatus.setStatusCode(status.getStatusCode());
        return statusRepository.save(existingStatus);
    }

    @Override
    public Status getStatusByCode(String statusCode) throws StatusNotFoundException {
        return statusRepository.findById(statusCode).orElseThrow(StatusNotFoundException::new);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public void deleteStatus(String statusCode) throws StatusNotFoundException {
        Status status = statusRepository.findById(statusCode).orElseThrow(StatusNotFoundException::new);
        statusRepository.delete(status);
    }
}
