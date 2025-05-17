package com.cinebuzz.controllers;

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

import com.cinebuzz.dto.LoginDto;
import com.cinebuzz.dto.ResponseToken;
import com.cinebuzz.entities.User;
import com.cinebuzz.exceptions.InvalidCredentialsException;
import com.cinebuzz.security.JwtUtils;
import com.cinebuzz.services.UserService;

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
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		if (authentication.isAuthenticated()) {
			User user = userService.findByNameOrEmail(loginDto.getUsername(), loginDto.getUsername());
			Map<String, Object> claims = new HashMap<>();
			claims.put("name", user.getName());
			claims.put("email", user.getEmail());
			claims.put("userid", user.getUserId());
			claims.put("usertype", user.getUserType());
			String token = jwtService.generateToken(loginDto.getUsername(), claims);
			ResponseToken responseToken = new ResponseToken(token);
			return ResponseEntity.status(HttpStatus.OK).body(responseToken);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Authorized !!");
	} 
	catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
    }
	}
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user)  {
		if (userService.existsByName(user.getName()) || userService.existsByEmail(user.getEmail()))
			throw new InvalidCredentialsException("Username or Email Exists !");
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
	}
}
