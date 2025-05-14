package com.capgemini.CineBuzz.services;

import java.util.List;

import com.capgemini.CineBuzz.entities.User;


public interface UserService {
	List<User> getAllUsers();

	User getUserById(Long userId);

	User createUser(User user);
	
    User patchUser(Long userId, User user);

	User updateUser(Long userId, User user);

	boolean deleteUser(Long userId);
}
