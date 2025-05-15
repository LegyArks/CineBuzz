package com.capgemini.CineBuzz.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;
import com.capgemini.CineBuzz.repositories.UserRepository;

@Service
public class LoginDtoServiceImpl implements LoginDtoService{

	

	UserRepository userRepository;

	@Autowired
	public LoginDtoServiceImpl(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	
	
	public User authenticateUser(LoginDto loginDto) throws InvalidCredentialsException {
	    Optional<User> userOptional = userRepository.findByEmailAndPassword(
	        loginDto.getEmail(), 
	        loginDto.getPassword()
	    );

	    User user = userOptional.orElseThrow(() -> 
	        new InvalidCredentialsException("Invalid email or password")
	    );

	    // Ensure userType is not null
	    if (user.getUserType() == null) {
	        throw new InvalidCredentialsException("User type not set");
	    }

	    return user;
	}

	

	


	
	
	
}
