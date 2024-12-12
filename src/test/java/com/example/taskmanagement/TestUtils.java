package com.example.taskmanagement;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.user.domain.UserEntity;

public class TestUtils {

  private TestUtils() {}

  public static UserEntity createTestUser(
      String name, String lastName, String phone, String email) {
    UserEntity user = new UserEntity();
    user.setFirstName(name);
    user.setLastName(lastName);
    user.setPhoneNumber(phone);
    user.setEmail(email);
    return user;
  }

  public static TaskEntity createTestTask(UserEntity assignee) {
    return TaskEntity.builder()
        .status("TODO")
        .title("Test task")
        .assignee(assignee)
        .description("Test task")
        .build();
  }
}
