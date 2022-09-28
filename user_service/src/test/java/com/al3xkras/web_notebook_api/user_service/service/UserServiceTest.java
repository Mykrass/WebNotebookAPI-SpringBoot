package com.al3xkras.web_notebook_api.user_service.service;

import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.exception.UsernameExistsException;
import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.al3xkras.web_notebook_api.user_service.model.TestEntities.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    @Order(0)
    void testProcessOAuthPostLogin(){

        String email1 = user1.getEmail();
        String email2 = user2.getEmail();
        Long id1 = 1L;
        Long id2 = 2L;
        User expected = User.builder()
                .username(email1)
                .detailsProvider(UserDetailsProvider.GOOGLE)
                .build();
        User saved = User.builder()
                .userId(id1)
                .username(email1)
                .detailsProvider(UserDetailsProvider.GOOGLE)
                .build();

        User expected2 = User.builder()
                .userId(id2)
                .username(email1)
                .detailsProvider(UserDetailsProvider.LOCAL)
                .build();

        Mockito.when(userRepository.findByUsername(email1))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.saveAndFlush(expected))
                .thenReturn(saved);

        Mockito.when(userRepository.findByUsername(email2))
                .thenReturn(Optional.of(expected2));

        assertEquals(saved,userService.processOAuthPostLogin(email1));

        assertThrows(ResponseStatusException.class,()->{
            userService.processOAuthPostLogin(email2);
        });
    }

    @Test
    @Order(10)
    void testFindByEmailAddress(){
        Mockito.when(userRepository.findByEmail(user1.getEmail()))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(user2.getEmail()))
                .thenReturn(Optional.empty());

        assertEquals(Optional.of(user1),userService.findByEmailAddress(user1.getEmail()));
        assertEquals(Optional.empty(),userService.findByEmailAddress(user2.getEmail()));
    }

    @Test
    @Order(20)
    void testSaveUser(){
        Mockito.when(userRepository.saveAndFlush(user1))
                .thenReturn(user1);
        Mockito.when(userRepository.saveAndFlush(user2))
                .thenThrow(DataIntegrityViolationException.class);

        assertEquals(user1,userService.saveUser(user1));
        assertThrows(UsernameExistsException.class,()->{
            userService.saveUser(user2);
        });

    }
}