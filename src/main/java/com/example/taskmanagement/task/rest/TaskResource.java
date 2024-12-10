package com.example.taskmanagement.task.rest;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.task.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskResource {

  private final TaskService taskService;

  @PostMapping("/tasks")
  public TaskDto createTask(@RequestBody TaskEntity taskEntity) {
    return taskService.createUser(taskEntity);
  }

  @GetMapping("/tasks")
  public List<TaskDto> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/tasks/{id}")
  public TaskDto getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
  }
}
