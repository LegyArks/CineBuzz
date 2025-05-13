package com.capgemini.CineBuzz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.Register;

public interface RegisterRepository extends JpaRepository<Register, Long>{
	
	Optional<Register> findByEmailAndPassword(String email, String password);
}
