package com.example.taskmanagement.task.mapper;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {

  @Mapping(target = "assignee", qualifiedByName = "mapWithoutAssigneeTasks")
  TaskDto toDto(TaskEntity taskEntity);

  TaskEntity toEntity(TaskDto taskDto);

  @Named("mapWithoutAssigneeTasks")
  default UserDto mapWithoutAssigneeTasks(UserEntity userEntity) {
    if (userEntity == null) {
      return null;
    }
    return UserDto.builder()
        .id(userEntity.getId())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .email(userEntity.getEmail())
        .phoneNumber(userEntity.getPhoneNumber())
        .build();
  }
}
