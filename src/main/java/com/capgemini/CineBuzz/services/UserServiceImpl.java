package com.capgemini.CineBuzz.services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.UserNotFoundException;
import com.capgemini.CineBuzz.repositories.UserRepository;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
   
   
    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with id " + id);
                });
    }

    @Override
    public User createUser(User user) {
        log.info("Creating new user with email: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update. User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with id " + id);
                });

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhoneNumber(updatedUser.getphoneNumber());

        log.info("Saving updated user with ID: {}", id);
        return userRepository.save(existingUser);
    }

    @Override
    public User patchUser(Long id, User patch) {
        log.info("Patching user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot patch. User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });

        if (patch.getName() != null) {
            existingUser.setName(patch.getName());
        }
        if (patch.getEmail() != null) {
            existingUser.setEmail(patch.getEmail());
        }
        if (patch.getPassword() != null) {
            existingUser.setPassword(patch.getPassword());
        }
        if (patch.getphoneNumber() != null) {
            existingUser.setPhoneNumber(patch.getphoneNumber());
        }

        log.info("Saving patched user with ID: {}", id);
        return userRepository.save(existingUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Cannot delete. User not found with ID: {}", id);
            throw new UserNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted with ID: {}", id);
        return true;
    }

    @Override
    public User authenticateUser(String email, String password) {
        log.info("Authenticating user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });

        if (!user.getPassword().equals(password)) {
            log.warn("Invalid password for user with email: {}", email);
            throw new RuntimeException("Invalid password");
        }

        log.info("Authentication successful for user with email: {}", email);
        return user;
    }

    @Override
    public boolean emailExists(String email) {
        log.info("Checking if email exists: {}", email);
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
    }
}
