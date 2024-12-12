package com.example.taskmanagement.user.service;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.repository.TaskRepository;
import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import com.example.taskmanagement.user.mapper.UserMapper;
import com.example.taskmanagement.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final TaskRepository taskRepository;

  public List<UserDto> getAllUsers() {
    return userRepository.findAllWithTasks().stream().map(userMapper::toDto).toList();
  }

  public UserDto createUser(UserEntity userEntity) {
    return userMapper.toDto(userRepository.save(userEntity));
  }

  public UserDto getUserById(Long id) {
    return userRepository
        .findById(id)
        .map(userMapper::toDto)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public void assignTask(Long userId, Long taskId) {
    UserEntity userEntity =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    TaskEntity task =
        taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    task.setAssignee(userEntity);
    taskRepository.save(task);
  }
}
