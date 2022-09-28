package com.al3xkras.web_notebook_api.user_service.repository;

import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.model.TestEntities;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.al3xkras.web_notebook_api.user_service.model.TestEntities.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        long id1 = user1.getUserId();
        user1.setUserId(null);
        testEntityManager.persist(user1);
        assertEquals(id1,user1.getUserId());
    }

    @Test
    @Order(0)
    void testAddUser(){
        long id1 = user2.getUserId();
        user2.setUserId(null);
        User user2added = userRepository.saveAndFlush(user2);
        user2.setUserId(id1);
        assertEquals(user2,user2added);

        assertThrows(DataIntegrityViolationException.class,()->{
            userRepository.saveAndFlush(userWithExistingUsername);
        });
        assertThrows(DataIntegrityViolationException.class,()->{
            userRepository.saveAndFlush(userWithExistingEmail);
        });
    }

    @Test
    @Order(10)
    void testDeleteUser(){
        assertThrows(EmptyResultDataAccessException.class,()->{
           userRepository.deleteById(user3.getUserId());
        });
        assertNotNull(userRepository.findByUsername(user1.getUsername()));
        userRepository.deleteById(user1.getUserId());
        assertEquals(Optional.empty(),userRepository.findByUsername(user1.getUsername()));
    }

    @Test
    @Order(20)
    void testFindByUsername(){
        assertEquals(Optional.empty(),userRepository.findByUsername(user2.getUsername()));
        assertEquals(Optional.empty(),userRepository.findByUsername(user3.getUsername()));
        user2.setUserId(null);
        userRepository.saveAndFlush(user2);
        assertEquals(Optional.of(user2),userRepository.findByUsername(user2.getUsername()));
        assertEquals(Optional.empty(),userRepository.findByUsername(user3.getUsername()));
        userRepository.deleteById(user1.getUserId());
        assertEquals(Optional.empty(),userRepository.findByUsername(user1.getUsername()));
    }

    @Test
    @Order(30)
    void testFindByEmail(){
        assertEquals(Optional.empty(),userRepository.findByEmail(user2.getEmail()));
        assertEquals(Optional.empty(),userRepository.findByEmail(user3.getEmail()));
        user2.setUserId(null);
        userRepository.saveAndFlush(user2);
        assertEquals(Optional.of(user2),userRepository.findByEmail(user2.getEmail()));
        assertEquals(Optional.empty(),userRepository.findByEmail(user3.getEmail()));
        userRepository.deleteById(user1.getUserId());
        assertEquals(Optional.empty(),userRepository.findByEmail(user1.getEmail()));
    }



}