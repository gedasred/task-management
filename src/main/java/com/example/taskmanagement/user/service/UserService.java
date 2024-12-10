package com.example.taskmanagement.user.service;

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

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toDto).toList();
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
}