package com.example.taskmanagement.task.rest;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.task.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskResource {

  private final TaskService taskService;

  @PostMapping("/tasks")
  public TaskDto createTask(@RequestBody TaskDto taskDto) {
    return taskService.createUser(taskDto);
  }

  @GetMapping("/tasks")
  public List<TaskDto> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/tasks/{taskId}")
  public TaskDto getTaskById(@PathVariable Long taskId) {
    return taskService.getTaskById(taskId);
  }

  @PutMapping("/tasks/{taskId}/assign-task-to-user/{userId}")
  public void assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
    taskService.assignTask(taskId, userId);
  }

  @PutMapping("/tasks/{taskId}/unassign-user")
  public void unassignUser(@PathVariable Long taskId) {
    taskService.unassignUser(taskId);
  }

  @DeleteMapping("/tasks/{taskId}")
  public void deleteTaskById(@PathVariable Long taskId) {
    taskService.deleteTaskById(taskId);
  }
}
