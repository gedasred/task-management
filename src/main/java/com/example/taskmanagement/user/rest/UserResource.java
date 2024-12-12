package com.example.taskmanagement.user.rest;

import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import com.example.taskmanagement.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserResource {

  private final UserService userService;

  @GetMapping("/users")
  public List<UserDto> getUsers() {
    log.info("Getting all users v3");
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping("/users")
  public UserDto createUser(@RequestBody UserEntity userEntity) {
    return userService.createUser(userEntity);
  }

  @PutMapping("/users/{userId}/assign-task/{taskId}")
  public void assignTask(@PathVariable Long userId, @PathVariable Long taskId) {
    userService.assignTask(userId, taskId);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
  }
}
