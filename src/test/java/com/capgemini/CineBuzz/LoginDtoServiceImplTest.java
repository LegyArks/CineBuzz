package com.capgemini.CineBuzz;


import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.repositories.UserRepository;
import com.capgemini.CineBuzz.services.LoginDtoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginDtoServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginDtoServiceImpl loginDtoService;

    private User sampleUser;
    private LoginDto validLoginDto;
    private LoginDto invalidLoginDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleUser = new User();
        sampleUser.setUserId(1L);
        sampleUser.setEmail("user@example.com");
        sampleUser.setPassword("password123");
        sampleUser.setUserType("Admin");

        validLoginDto = new LoginDto();
        validLoginDto.setEmail("user@example.com");
        validLoginDto.setPassword("password123");

        invalidLoginDto = new LoginDto();
        invalidLoginDto.setEmail("wrong@example.com");
        invalidLoginDto.setPassword("wrongpass");
    }

    @Test
    void testAuthenticateUser_ValidCredentials() throws InvalidCredentialsException {
        when(userRepository.findByEmailAndPassword(
                validLoginDto.getEmail(),
                validLoginDto.getPassword()
        )).thenReturn(Optional.of(sampleUser));

        User result = loginDtoService.authenticateUser(validLoginDto);

        assertNotNull(result);
        assertEquals("Admin", result.getUserType());
        assertEquals("user@example.com", result.getEmail());
    }


    @Test
    void testAuthenticateUser_InvalidCredentials() {
        when(userRepository.findByEmailAndPassword(
                invalidLoginDto.getEmail(),
                invalidLoginDto.getPassword()
        )).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () ->
                loginDtoService.authenticateUser(invalidLoginDto));
    }

    @Test
    void testAuthenticateUser_NullUserType() {
        sampleUser.setUserType(null);

        when(userRepository.findByEmailAndPassword(
                validLoginDto.getEmail(),
                validLoginDto.getPassword()
        )).thenReturn(Optional.of(sampleUser));

        assertThrows(InvalidCredentialsException.class, () ->
                loginDtoService.authenticateUser(validLoginDto));
    }
}
