package com.example.taskmanagement.user.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class UserResourceTest {

  private static final String USERS = "/users";
  private static final String FIRST_NAME = "John";
  private static final String LAST_NAME = "Doe";
  private static final String PHONE_NUMBER = "+37066666666";
  private static final String EMAIL = "jo@do.com";

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;
  @Autowired private TaskRepository taskRepository;

  @AfterEach
  void cleanUp() {
    taskRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "test_user")
  void testCreateUser() throws Exception {
    String userJson = new ObjectMapper().writeValueAsString(createTestUser());

    createUserPostCall(userJson);

    mockMvc
        .perform(get(USERS).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is(FIRST_NAME)))
        .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)))
        .andExpect(jsonPath("$[0].phoneNumber", is(PHONE_NUMBER)))
        .andExpect(jsonPath("$[0].email", is(EMAIL)));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testGetUserById() throws Exception {
    String userJson = new ObjectMapper().writeValueAsString(createTestUser());

    createUserPostCall(userJson);

    Long id = userRepository.findAll().get(0).getId();

    mockMvc
        .perform(
            get("%s/%d".formatted(USERS, id)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is(FIRST_NAME)))
        .andExpect(jsonPath("lastName", is(LAST_NAME)))
        .andExpect(jsonPath("phoneNumber", is(PHONE_NUMBER)))
        .andExpect(jsonPath("email", is(EMAIL)));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testGetUserById_notFoundException() throws Exception {
    mockMvc
        .perform(
            get("%s/%d".formatted(USERS, 1)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("status", is(HttpStatus.NOT_FOUND.name())))
        .andExpect(jsonPath("message", is("User not found")));
  }

  @Test
  @WithMockUser(username = "test_user")
  void testDeleteUserById() throws Exception {
    String userJson = new ObjectMapper().writeValueAsString(createTestUser());

    createUserPostCall(userJson);

    Long id = userRepository.findAll().get(0).getId();

    mockMvc
        .perform(
            delete("%s/%d".formatted(USERS, id))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
        .andExpect(status().isOk());

    Assertions.assertTrue(userRepository.findAll().isEmpty());
  }

  @Test
  @WithMockUser(username = "test_user")
  void testDeleteUserById_userHasTaskException() throws Exception {
    String userJson = new ObjectMapper().writeValueAsString(createTestUser());
    createUserPostCall(userJson);

    UserEntity user = userRepository.findAll().get(0);
    TaskEntity task = TestUtils.createTestTask(user);
    taskRepository.save(task);

    mockMvc
        .perform(
            delete("%s/%d".formatted(USERS, user.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("status", is(HttpStatus.BAD_REQUEST.name())))
        .andExpect(jsonPath("message", is("User can not be deleted. Has tasks assigned.")));
  }

  private void createUserPostCall(String userJson) throws Exception {
    mockMvc
        .perform(post(USERS).contentType(MediaType.APPLICATION_JSON).content(userJson).with(csrf()))
        .andExpect(status().isOk());
  }

  private static UserEntity createTestUser() {
    return TestUtils.createTestUser(FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL);
  }
}
