package com.example.taskmanagement.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ParentException {
  public UserNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
