package com.example.taskmanagement.task.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskmanagement.TestUtils;
import com.example.taskmanagement.task.domain.TaskEntity;
import com.example.taskmanagement.task.repository.TaskRepository;
import com.example.taskmanagement.user.domain.UserEntity;
import com.example.taskmanagement.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskResourceTest {

  private static final String TASKS = "/tasks";
  private static final String TASK_TITLE = "Task title";
  private static final String TASK_DESCRIPTION = "Task description";
  private static final String TASK_STATUS = "Task status";
  @Autowired private MockMvc mockMvc;

  @Autowired private TaskRepository taskRepository;
  @Autowired private UserRepository userRepository;

  @AfterEach
  void cleanUp() {
    taskRepository.deleteAll();
    userRepository.deleteAll();
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
        .perform(
            get("%s/%d".formatted(TASKS, id)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title", is(TASK_TITLE)))
        .andExpect(jsonPath("description", is(TASK_DESCRIPTION)))
        .andExpect(jsonPath("status", is(TASK_STATUS)));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testGetUserById_notFoundException() throws Exception {
    mockMvc
        .perform(
            get("%s/%d".formatted(TASKS, 1)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status", is(HttpStatus.NOT_FOUND.name())))
        .andExpect(jsonPath("message", is("Task not found")));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testDeleteTaskById() throws Exception {
    String taskJson = new ObjectMapper().writeValueAsString(createTestTask());
    createTaskPostCall(taskJson);

    Long id = taskRepository.findAll().get(0).getId();

    mockMvc
        .perform(
            delete("%s/%d".formatted(TASKS, id))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
        .andExpect(status().isOk());

    mockMvc
        .perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testAssignTaskToUser() throws Exception {
    String taskJson = new ObjectMapper().writeValueAsString(createTestTask());
    createTaskPostCall(taskJson);

    Long taskId = taskRepository.findAll().get(0).getId();
    Long userId = createTestUser().getId();

    mockMvc
        .perform(
            put("%s/%d/assign-task-to-user/%d".formatted(TASKS, taskId, userId))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
        .andExpect(status().isOk());

    TaskEntity updatedTask = taskRepository.findById(taskId).get();
    Assertions.assertEquals(userId, updatedTask.getAssignee().getId());
  }

  @Test
  @WithMockUser(username = "test_user")
  void testUnassignTask() throws Exception {
    String taskJson = new ObjectMapper().writeValueAsString(createTestTask());
    createTaskPostCall(taskJson);
    TaskEntity task = taskRepository.findAll().get(0);
    Long taskId = task.getId();
    task.setAssignee(createTestUser());
    taskRepository.save(task);

    mockMvc
        .perform(
            put("%s/%d/unassign-user".formatted(TASKS, taskId))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
        .andExpect(status().isOk());

    TaskEntity updatedTask = taskRepository.findById(taskId).get();
    Assertions.assertNull(updatedTask.getAssignee());
  }

  private UserEntity createTestUser() {
    UserEntity testUser = TestUtils.createTestUser("name", "surname", "email", "phone");
    return userRepository.save(testUser);
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
