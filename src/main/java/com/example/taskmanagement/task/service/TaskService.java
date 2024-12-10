package com.example.taskmanagement.task.service;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.task.mapper.TaskMapper;
import com.example.taskmanagement.task.repository.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  public List<TaskDto> getAllTasks() {
    return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
  }

  public TaskDto createUser(TaskEntity taskEntity) {
    return taskMapper.toDto(taskRepository.save(taskEntity));
  }

  public TaskDto getTaskById(Long id) {
    return taskRepository
        .findById(id)
        .map(taskMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Task not found"));
  }
}
