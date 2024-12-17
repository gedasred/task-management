package com.example.taskmanagement.exceptions.handler;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {}
