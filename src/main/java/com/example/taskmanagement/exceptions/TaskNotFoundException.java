package com.example.taskmanagement.exceptions;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends ParentException {
  public TaskNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
