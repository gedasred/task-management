package com.example.taskmanagement.user.dto;

import com.example.taskmanagement.task.dto.TaskDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private List<TaskDto> tasks;
}
