package com.example.taskmanagement.task.service;

import com.example.taskmanagement.exceptions.TaskNotFoundException;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.task.mapper.TaskMapper;
import com.example.taskmanagement.task.repository.TaskRepository;
import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TaskMapper taskMapper;

  public List<TaskDto> getAllTasks() {
    return taskRepository.findAllWithAssignee().stream().map(taskMapper::toDto).toList();
  }

  public TaskDto createUser(TaskDto taskDto) {
    TaskEntity taskEntity = taskMapper.toEntity(taskDto);
    return taskMapper.toDto(taskRepository.save(taskEntity));
  }

  public TaskDto getTaskById(Long id) {
    return taskRepository
        .findById(id)
        .map(taskMapper::toDto)
        .orElseThrow(() -> new TaskNotFoundException("Task not found"));
  }

  public void assignTask(Long taskId, Long userId) {
    UserEntity userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    TaskEntity task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    task.setAssignee(userEntity);
    taskRepository.save(task);
  }

  public void unassignUser(Long taskId) {
    TaskEntity task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    task.setAssignee(null);
    taskRepository.save(task);
  }

  public void deleteTaskById(Long taskId) {
    taskRepository.deleteById(taskId);
  }
}
