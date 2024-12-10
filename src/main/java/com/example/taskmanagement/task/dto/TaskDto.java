package com.example.taskmanagement.task.dto;

import com.example.taskmanagement.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDto {

  private Long id;
  private String title;
  private String description;
  private String status;
  private UserDto assignee;

}
