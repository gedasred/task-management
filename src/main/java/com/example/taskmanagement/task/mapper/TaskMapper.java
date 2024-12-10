package com.example.taskmanagement.task.mapper;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {

  TaskDto toDto(TaskEntity taskEntity);

  TaskEntity toEntity(TaskDto taskDto);
}
