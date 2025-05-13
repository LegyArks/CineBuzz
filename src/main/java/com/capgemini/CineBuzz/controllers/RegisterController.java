package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.entities.Register;
import com.capgemini.CineBuzz.services.RegisterService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class RegisterController {
	RegisterService registerService;

	@Autowired
	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}
	
	@PostMapping
	public ResponseEntity<Register> addUser(@RequestBody Register register) {
		return ResponseEntity.status(HttpStatus.CREATED).body(registerService.addUser(register));
	}
}
