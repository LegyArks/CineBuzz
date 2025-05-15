package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.services.UserService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/users/" + savedUser.getUserId()))
				.body(savedUser);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
		User updatedUser = userService.updateUser(userId, user);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User user) {
		User patched = userService.patchUser(id, user);
		return ResponseEntity.ok(patched);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		boolean deleted = userService.deleteUser(id);
		if (deleted) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		User user = userService.findByEmail(email);
		return ResponseEntity.ok(user);
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
//		String email = credentials.get("email");
//		String password = credentials.get("password");
//
//		try {
//			User user = userService.authenticateUser(email, password);
//			return ResponseEntity.ok(user);
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//		}
//	}
//
//	@PostMapping("/register")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody User userDTO) {
//		// Check if email already exists
//		if (userService.emailExists(userDTO.getEmail())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
//		}
//
//		// Convert DTO to Entity
//		User user = new User();
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setPhoneNumber(userDTO.getphoneNumber());
//
//		User savedUser = userService.createUser(user);
//		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/users/" + savedUser.getUserId()))
//				.body(savedUser);
//	}

	

}
