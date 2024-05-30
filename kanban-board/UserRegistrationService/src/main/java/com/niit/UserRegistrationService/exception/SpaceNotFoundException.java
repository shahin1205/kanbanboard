package com.niit.UserRegistrationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Space Not Found")
public class SpaceNotFoundException extends Exception {
}
