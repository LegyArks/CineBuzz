package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.dto.LoginDto;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.InvalidCredentialsException;

public interface LoginDtoService {

	 User authenticateUser(LoginDto loginDto) throws InvalidCredentialsException;
}
