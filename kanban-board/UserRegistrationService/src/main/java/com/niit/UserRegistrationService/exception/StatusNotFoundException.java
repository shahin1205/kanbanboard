package com.niit.UserRegistrationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Status Not Found")
public class StatusNotFoundException extends Exception{
}
