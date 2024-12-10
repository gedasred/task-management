package com.example.taskmanagement.task.repository;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {}
