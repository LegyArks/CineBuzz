package com.capgemini.CineBuzz.services;

import com.capgemini.CineBuzz.repositories.RegisterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.Register;

@Service
public class RegisterServiceImpl implements RegisterService{

	RegisterRepository registerRepo;
	
	@Autowired
	public RegisterServiceImpl(RegisterRepository registerRepo) {
		this.registerRepo = registerRepo;
	}

	public Register addUser(Register register) {
		return registerRepo.save(register);
	}
	
	
}
