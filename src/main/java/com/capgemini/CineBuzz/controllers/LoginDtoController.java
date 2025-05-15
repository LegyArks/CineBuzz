package com.capgemini.CineBuzz.controllers;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.services.LoginDtoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class LoginDtoController {

    private final LoginDtoService loginDtoService;

    public LoginDtoController(LoginDtoService loginDtoService) {
        this.loginDtoService = loginDtoService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> handleLogin(@Valid @RequestBody LoginDto loginDto) 
        throws InvalidCredentialsException {
        User user = loginDtoService.authenticateUser(loginDto);
        return ResponseEntity.ok()
            .header("User-Type", user.getUserType()) 
            .body(user);
    }
}