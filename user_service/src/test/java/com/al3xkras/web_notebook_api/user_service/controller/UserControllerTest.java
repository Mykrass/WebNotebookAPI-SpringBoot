package com.al3xkras.web_notebook_api.user_service.controller;

import com.al3xkras.web_notebook_api.user_service.dto.UserDTO;
import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.model.UserType;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.al3xkras.web_notebook_api.user_service.model.TestEntities.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    String usernameRegex = "[a-zA-Z0-9_]{1,15}";
    String invalidUsernameRegex = "(?!([a-zA-Z0-9_]{1,15})).+";
    List<UserDTO> generateValid(int count){
        Faker faker = new Faker();
        List<UserDTO> valid = new ArrayList<>();
        IntStream.range(0,count).forEach(i-> valid.add(UserDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .username(faker.regexify(usernameRegex))
                .build()));
        return valid;
    }

    List<UserDTO> generateWithInvalidEmail(int count){
        Faker faker = new Faker();
        List<UserDTO> invalid = new ArrayList<>();
        IntStream.range(0,count).forEach(i-> invalid.add(UserDTO.builder()
                .email(faker.regexify(invalidUsernameRegex))
                .password(faker.internet().password())
                .username(faker.regexify(usernameRegex))
                .build()));
        return invalid;
    }

    List<UserDTO> generateWithInvalidUsername(int count){
        Faker faker = new Faker();
        List<UserDTO> invalid = new ArrayList<>();
        IntStream.range(0,count).forEach(i-> invalid.add(UserDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .username(faker.regexify(invalidUsernameRegex))
                .build()));
        return invalid;
    }

    @Test
    void register() throws Exception {
        log.info(generateValid(10).toString());
        log.info(generateWithInvalidUsername(10).toString());
        log.info(generateWithInvalidEmail(10).toString());

        long userId = 1L;
        for (UserDTO valid : generateValid(10)){
            User expected = User.builder()
                    .username(valid.getUsername())
                    .email(valid.getEmail())
                    .password(valid.getPassword())
                    .detailsProvider(UserDetailsProvider.LOCAL)
                    .userType(UserType.USER)
                    .userId(userId)
                    .build();
            Mockito.when(userService.saveUser(expected))
                    .thenReturn(expected);
            mockMvc.perform(post("/u/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(valid)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }
}