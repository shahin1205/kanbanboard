package com.niit.UserRegistrationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Project Not Found")
public class ProjectNotFoundException extends Exception{
}
