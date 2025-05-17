package com.cinebuzz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.cinebuzz.entities.User;
import com.cinebuzz.exceptions.UserNotFoundException;
import com.cinebuzz.repositories.UserRepository;
import com.cinebuzz.services.UserServiceImpl;

import java.util.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John Doe", "john@example.com", "pass123", "9876543210", "user");
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertEquals("John Doe", found.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User created = userService.createUser(user);

        assertEquals("john@example.com", created.getEmail());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertTrue(userService.deleteUser(1L));
    }

    /*@Test
    void testAuthenticateUser_Success() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User result = userService.authenticateUser("john@example.com", "pass123");

        assertEquals("John Doe", result.getName());
    }*/
}
