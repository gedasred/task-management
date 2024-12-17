package com.example.taskmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ParentException extends RuntimeException {

  private final HttpStatus status;

  public ParentException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
