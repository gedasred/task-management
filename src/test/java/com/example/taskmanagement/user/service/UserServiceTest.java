package com.example.taskmanagement.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import com.example.taskmanagement.user.mapper.UserMapper;
import com.example.taskmanagement.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.CapturesArguments;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;
  @Mock UserMapper userMapper;

  @InjectMocks UserService userService;

  @Test
  void createUser() {

    String name = "John";

    String mail = "e@e.com";
    UserDto userDto =
        UserDto.builder()
            .firstName(name)
            .lastName("Doe")
            .email(mail)
            .phoneNumber("+37066666666")
            .build();

    UserEntity userEntity =
        UserEntity.builder()
            .firstName(name)
            .lastName("Doe")
            .email(mail)
            .phoneNumber("+37066666666")
            .build();

    when(userMapper.toEntity(userDto)).thenReturn(userEntity);
    when(userMapper.toDto(userEntity)).thenReturn(userDto);

    when(userRepository.save(userEntity)).thenReturn(userEntity);

    UserDto user = userService.createUser(userDto);

    assertNotNull(user);

    assertEquals(name, user.getFirstName());
    assertEquals(mail, user.getEmail());

  }
}
