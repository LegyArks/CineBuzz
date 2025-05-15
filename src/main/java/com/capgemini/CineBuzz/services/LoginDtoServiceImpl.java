package com.capgemini.CineBuzz.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginDtoServiceImpl implements LoginDtoService {

    private final UserRepository userRepository;

    @Autowired
    public LoginDtoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(LoginDto loginDto) throws InvalidCredentialsException {
        log.info("Authenticating user with email: {}", loginDto.getEmail());

        Optional<User> userOptional = userRepository.findByEmailAndPassword(
            loginDto.getEmail(),
            loginDto.getPassword()
        );

        User user = userOptional.orElseThrow(() -> {
            log.warn("Invalid login attempt for email: {}", loginDto.getEmail());
            return new InvalidCredentialsException("Invalid email or password");
        });

        // Ensure userType is not null
        if (user.getUserType() == null) {
            log.error("User type is not set for user: {}", loginDto.getEmail());
            throw new InvalidCredentialsException("User type not set");
        }

        log.info("User authenticated successfully with email: {}", loginDto.getEmail());
        return user;
    }
}
