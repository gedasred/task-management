package com.example.taskmanagement.user.repository;

import com.example.taskmanagement.user.domain.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.tasks")
  List<UserEntity> findAllWithTasks();

}
