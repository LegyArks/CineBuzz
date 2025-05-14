package com.capgemini.CineBuzz.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.exceptions.UserNotFoundException;
import com.capgemini.CineBuzz.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
   
    
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhoneNumber(updatedUser.getphoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public User patchUser(Long id, User patch) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (patch.getName() != null) {
            existingUser.setName(patch.getName());
        }
        if (patch.getEmail() != null) {
            existingUser.setEmail(patch.getEmail());
        }
        if (patch.getPassword() != null) {
            existingUser.setPassword(patch.getPassword());
        }
        if (patch.getphoneNumber() != null) {
            existingUser.setPhoneNumber(patch.getphoneNumber());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
        return true;
    }
    
    @Override
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        
        return user;
    }
    
    
    // NEW METHOD IMPLEMENTATION ADDED
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

   
}
