package com.capgemini.CineBuzz.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.dto.ResponseToken;
import com.capgemini.CineBuzz.entities.User;
<<<<<<< HEAD
import com.capgemini.CineBuzz.exceptions.UserAlreadyExistsException;
import com.capgemini.CineBuzz.security.JwtUtils;
import com.capgemini.CineBuzz.services.UserService;


=======
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.security.JwtUtils;
import com.capgemini.CineBuzz.services.UserService;

>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
@RestController
@RequestMapping("/auth")
public class AuthController {

	AuthenticationManager authenticationManager;
	UserService userService;
	PasswordEncoder passwordEncoder;
	JwtUtils jwtService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService,
			PasswordEncoder passwordEncoder, JwtUtils jwtService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
		try {
		Authentication authentication = authenticationManager
<<<<<<< HEAD
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		if (authentication.isAuthenticated()) {
			User user = userService.findByNameOrEmail(loginDto.getEmail(), loginDto.getEmail());
=======
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		if (authentication.isAuthenticated()) {
			User user = userService.findByNameOrEmail(loginDto.getUsername(), loginDto.getUsername());
>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
			Map<String, Object> claims = new HashMap<>();
			claims.put("name", user.getName());
			claims.put("email", user.getEmail());
			claims.put("userid", user.getUserId());
			claims.put("usertype", user.getUserType());
<<<<<<< HEAD
			String token = jwtService.generateToken(loginDto.getEmail(), claims);
=======
			String token = jwtService.generateToken(loginDto.getUsername(), claims);
>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
			ResponseToken responseToken = new ResponseToken(token);
			return ResponseEntity.status(HttpStatus.OK).body(responseToken);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Authorized !!");
	} 
	catch (Exception e) {
        e.printStackTrace(); // will show exact root cause
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
    }
	}
	@PostMapping("/register")
<<<<<<< HEAD
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (userService.existsByName(user.getName()) || userService.existsByEmail(user.getEmail()))
			throw new UserAlreadyExistsException("Username or Email Exists !");
=======
	public ResponseEntity<User> registerUser(@RequestBody User user)  {
		if (userService.existsByName(user.getName()) || userService.existsByEmail(user.getEmail()))
			throw new InvalidCredentialsException("Username or Email Exists !");
>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
	}
}

<<<<<<< HEAD

=======
>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
