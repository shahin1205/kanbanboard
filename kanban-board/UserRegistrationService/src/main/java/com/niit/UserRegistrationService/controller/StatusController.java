package com.niit.UserRegistrationService.controller;

import com.niit.UserRegistrationService.domain.Status;
import com.niit.UserRegistrationService.exception.StatusNotFoundException;
import com.niit.UserRegistrationService.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        return ResponseEntity.ok(statusService.createStatus(status));
    }

    @PutMapping("/{statusCode}")
    public ResponseEntity<Status> updateStatus(@PathVariable String statusCode, @RequestBody Status status) throws StatusNotFoundException {
        return ResponseEntity.ok(statusService.updateStatus(statusCode, status));
    }

    @GetMapping("/{statusCode}")
    public ResponseEntity<Status> getStatusByCode(@PathVariable String statusCode) throws StatusNotFoundException {
        return ResponseEntity.ok(statusService.getStatusByCode(statusCode));
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(statusService.getAllStatuses());
    }

    @DeleteMapping("/{statusCode}")
    public ResponseEntity<Void> deleteStatus(@PathVariable String statusCode) throws StatusNotFoundException {
        statusService.deleteStatus(statusCode);
        return ResponseEntity.noContent().build();
    }
}
