package com.example.taskmanagement.exceptions;

public class UserHasTasksException extends RuntimeException {
  public UserHasTasksException(String message) {
    super(message);
  }
}
