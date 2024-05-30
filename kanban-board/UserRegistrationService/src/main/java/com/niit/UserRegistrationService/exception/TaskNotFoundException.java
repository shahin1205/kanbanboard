package com.niit.UserRegistrationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Task Not Found")
public class TaskNotFoundException extends Exception {
}
