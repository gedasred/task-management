package com.example.taskmanagement.task.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskEntityResourceTest {

  private static final String TASKS = "/tasks";
  private static final String TASK_TITLE = "Task title";
  private static final String TASK_DESCRIPTION = "Task description";
  private static final String TASK_STATUS = "Task status";
  @Autowired private MockMvc mockMvc;

  @Autowired private TaskRepository taskRepository;

  @AfterEach
  void cleanUp() {
    taskRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "test_user")
  void testCreateTask() throws Exception {
    String taskJson = new ObjectMapper().writeValueAsString(createTestTask());
    createTaskPostCall(taskJson);

    mockMvc
        .perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is(TASK_TITLE)))
        .andExpect(jsonPath("$[0].description", is(TASK_DESCRIPTION)))
        .andExpect(jsonPath("$[0].status", is(TASK_STATUS)));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testGetUserById() throws Exception {
    String taskJson = new ObjectMapper().writeValueAsString(createTestTask());
    createTaskPostCall(taskJson);

    Long id = taskRepository.findAll().get(0).getId();

    mockMvc
        .perform(get(TASKS + "/" + id).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title", is(TASK_TITLE)))
        .andExpect(jsonPath("description", is(TASK_DESCRIPTION)))
        .andExpect(jsonPath("status", is(TASK_STATUS)));
  }

  private void createTaskPostCall(String taskJson) throws Exception {
    mockMvc
        .perform(post(TASKS).contentType(MediaType.APPLICATION_JSON).content(taskJson).with(csrf()))
        .andExpect(status().isOk());
  }

  private static TaskEntity createTestTask() {
    return TaskEntity.builder()
        .title(TASK_TITLE)
        .description(TASK_DESCRIPTION)
        .status(TASK_STATUS)
        .build();
  }
}
