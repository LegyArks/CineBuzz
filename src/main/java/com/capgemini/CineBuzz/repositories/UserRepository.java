package com.capgemini.CineBuzz.repositories;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.User;



public interface UserRepository extends JpaRepository<User, Long> {
 	 
	 Optional<User> findByEmail(String email);
	 
	 Optional<User> findByNameOrEmail(String username, String email);

		Optional<User> findByName(String username);

		boolean existsByName(String username);

		boolean existsByEmail(String email);
}


