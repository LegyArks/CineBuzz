package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.controllers.LoginDtoController;
import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.services.LoginDtoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginDtoControllerTest {

    @Mock
    private LoginDtoService loginDtoService;

    @InjectMocks
    private LoginDtoController loginDtoController;

    private LoginDto validLoginDto;
    private LoginDto invalidLoginDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validLoginDto = new LoginDto("user@example.com", "password123");
        invalidLoginDto = new LoginDto("wrong@example.com", "wrongpass");

        user = new User();
        user.setUserId(1L);
        user.setEmail("user@example.com");
        user.setUserType("Admin");
    }

    @Test
    void testHandleLogin_ValidCredentials() throws InvalidCredentialsException {
        when(loginDtoService.authenticateUser(validLoginDto)).thenReturn(user);

        ResponseEntity<User> response = loginDtoController.handleLogin(validLoginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals("Admin", response.getHeaders().getFirst("User-Type"));

        verify(loginDtoService).authenticateUser(validLoginDto);
    }

    @Test
    void testHandleLogin_InvalidCredentials() throws InvalidCredentialsException {
        when(loginDtoService.authenticateUser(invalidLoginDto))
            .thenThrow(new InvalidCredentialsException("Invalid credentials"));

        assertThrows(InvalidCredentialsException.class, () -> 
            loginDtoController.handleLogin(invalidLoginDto));

        verify(loginDtoService).authenticateUser(invalidLoginDto);
    }
}

