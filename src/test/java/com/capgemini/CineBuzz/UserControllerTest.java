package com.capgemini.CineBuzz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import com.capgemini.CineBuzz.controllers.UserController;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import java.util.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User(1L, "John Doe", "john@example.com", "pass123", "9876543210", "user");
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(sampleUser));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().get(0).getName());
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(sampleUser)).thenReturn(sampleUser);

        ResponseEntity<User> response = userController.createUser(sampleUser, null);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

}
