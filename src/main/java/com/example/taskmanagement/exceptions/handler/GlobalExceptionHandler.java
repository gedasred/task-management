package com.example.taskmanagement.exceptions.handler;

import com.example.taskmanagement.exceptions.ParentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ParentException.class)
  public ResponseEntity<ErrorResponse> handleException(ParentException ex) {
    ErrorResponse response = new ErrorResponse(ex.getStatus(), ex.getMessage());

    return new ResponseEntity<>(response, ex.getStatus());
  }
}
