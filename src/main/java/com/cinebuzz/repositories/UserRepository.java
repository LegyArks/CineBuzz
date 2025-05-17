package com.cinebuzz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebuzz.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {


	Optional<User> findByEmail(String email);

	Optional<User> findByNameOrEmail(String name, String email);

	boolean existsByEmail(String email);

	boolean existsByName(String name);

	Optional<User> findByName(String username);

}
