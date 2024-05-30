package com.niit.UserRegistrationService.config;

import com.niit.UserRegistrationService.domain.Status;
import com.niit.UserRegistrationService.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final StatusRepository statusRepository;

    @Autowired
    public ApplicationInitializer(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... args) {
        if (statusRepository.count() == 0) {
            Status todo = new Status("TODO");
            Status inProgress = new Status("IN_PROGRESS");
            Status completed = new Status("COMPLETED");

            statusRepository.saveAll(Arrays.asList(todo, inProgress, completed));
        }
    }
}
