package com.example.taskmanagement.exceptions;

import org.springframework.http.HttpStatus;

public class UserHasTasksException extends ParentException {
  public UserHasTasksException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
