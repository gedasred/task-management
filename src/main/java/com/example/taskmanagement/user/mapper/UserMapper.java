package com.example.taskmanagement.user.mapper;

import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

  UserDto toDto(UserEntity userEntity);

  UserEntity toEntity(UserDto userDto);
}
