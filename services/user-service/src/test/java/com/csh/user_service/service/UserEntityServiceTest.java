package com.csh.user_service.service;

import com.csh.user_service.UserTestDataInitializer;
import com.csh.user_service.entity.UserEntity;
import com.csh.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserEntityServiceTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestDataInitializer testDataInitializer;

    @Test
    @Transactional
    void testGetUserById_Success() {
        Optional<UserEntity> result = userService.getUserById("userId1");

        assertTrue(result.isPresent());
        assertEquals("userName", result.get().getUserName());
        assertEquals("user@mail.com", result.get().getEmail());
    }

    @Test
    @Transactional
    void testGetInventoryById_NotFound() {
        Optional<UserEntity> result = userService.getUserById("");

        assertFalse(result.isPresent());
    }

    @Test
    @Transactional
    void testCreateUser_Success() {
        UserEntity result = userService.createUser(new UserEntity("newUserId","passwd","newUserName","newUser@mail.com", "ROLE_ADMIN"));

        assertNotNull(result);
        assertEquals("newUserName", result.getUserName());
        assertEquals("newUser@mail.com", result.getEmail());
    }

    @Test
    @Transactional
    void testCreateUser_InvalidId() {
        UserEntity idCheckUserEntity = new UserEntity("userId1","passwd","newUserName","newUser@mail.com", "ROLE_ADMIN");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(idCheckUserEntity));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    @Transactional
    void testCreateUser_InvalidEmail() {
        UserEntity idCheckUserEntity = new UserEntity("newUserId","passwd","newUserName","user@mail.com", "ROLE_ADMIN");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(idCheckUserEntity));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}
