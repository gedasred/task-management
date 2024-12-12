package com.example.taskmanagement.user.mapper;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

  @Mapping(target = "tasks", qualifiedByName = "mapTasksWithoutAssignee")
  UserDto toDto(UserEntity userEntity);

  UserEntity toEntity(UserDto userDto);

  @Named("mapTasksWithoutAssignee")
  default List<TaskDto> mapTasksWithoutAssignee(List<TaskEntity> tasks) {
    if (CollectionUtils.isEmpty(tasks)) {
      return List.of();
    }
    return tasks.stream()
        .map(
            task ->
                TaskDto.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .status(task.getStatus())
                    .build())
        .toList();
  }
}
