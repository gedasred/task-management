package com.example.taskmanagement.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenResource {

  @GetMapping("/token")
  public String getCsrfToken(HttpServletRequest request) {
    return ((CsrfToken) request.getAttribute("_csrf")).getToken();
  }
}
