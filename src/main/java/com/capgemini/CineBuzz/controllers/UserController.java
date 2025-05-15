package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.BindingResult;

import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.services.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        log.info("Fetched {} users", users.size());
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            log.info("User found: {}", user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        log.info("Creating user with email: {}", user.getEmail());
        if (bindingResult.hasErrors()) {
            log.error("User creation failed due to validation errors: {}", bindingResult.getAllErrors());
            throw new IllegalArgumentException("Validation has failed");
        }
        User savedUser = userService.createUser(user);
        log.info("User created with ID: {}", savedUser.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/users/" + savedUser.getUserId()))
                .body(savedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User user, BindingResult bindingResult) {
        log.info("Updating user with ID: {}", userId);
        if (bindingResult.hasErrors()) {
            log.error("User update failed due to validation errors: {}", bindingResult.getAllErrors());
            throw new IllegalArgumentException("Validation has failed");
        }
        User updatedUser = userService.updateUser(userId, user);
        log.info("User updated successfully for ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult) {
        log.info("Patching user with ID: {}", id);
        if (bindingResult.hasErrors()) {
            log.error("User patch failed due to validation errors: {}", bindingResult.getAllErrors());
            throw new IllegalArgumentException("Validation has failed");
        }
        User patched = userService.patchUser(id, user);
        log.info("User patched successfully for ID: {}", id);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            log.info("User with ID {} deleted successfully", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.warn("Failed to delete. User with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        User user = userService.findByEmail(email);
        if (user != null) {
            log.info("User found: {}", user);
        } else {
            log.warn("User with email {} not found", email);
        }
        return ResponseEntity.ok(user);
    }
}
