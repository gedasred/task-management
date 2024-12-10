package com.example.taskmanagement.user.repository;

import com.example.taskmanagement.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}
