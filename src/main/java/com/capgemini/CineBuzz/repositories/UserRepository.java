package com.capgemini.CineBuzz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByEmail(String email);

	Optional<User> findByNameOrEmail(String name, String email);

	boolean existsByEmail(String email);

	boolean existsByName(String name);
=======
 	 
	 Optional<User> findByEmail(String email);
	 
	 Optional<User> findByNameOrEmail(String username, String email);

		Optional<User> findByName(String username);

		boolean existsByName(String username);

		boolean existsByEmail(String email);
>>>>>>> 3ce176ad05f6d62b9e9ada3aec6c006042a016c2
}
