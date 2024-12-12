package com.example.taskmanagement.task.repository;

import com.example.taskmanagement.task.domain.TaskEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  @EntityGraph(attributePaths = "assignee")
  @Query("SELECT t FROM TaskEntity t")
  List<TaskEntity> findAllWithAssignee();

  boolean existsByAssigneeId(Long assigneeId);
}
