package com.example.taskmanagement.exceptions.handler;

import com.example.taskmanagement.exceptions.TaskNotFoundException;
import com.example.taskmanagement.exceptions.UserHasTasksException;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());

    return new ResponseEntity<>(response, status);
  }

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(TaskNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());

    return new ResponseEntity<>(response, status);
  }

  @ExceptionHandler(UserHasTasksException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserHasTasksException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());

    return new ResponseEntity<>(response, status);
  }
}
