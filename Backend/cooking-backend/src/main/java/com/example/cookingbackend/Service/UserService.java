package com.example.cookingbackend.Service;

import com.example.cookingbackend.DTO.UserDTO;
import com.example.cookingbackend.Model.ActionLog;
import com.example.cookingbackend.Model.User;
import com.example.cookingbackend.Repository.UserRepository;
import com.example.cookingbackend.Repository.ActionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionLogRepository logRepo;

    public void log(User user, String action) {
        logRepo.save(new ActionLog(action, LocalDateTime.now(), user));
    }

    // Method to add a new user (signup)
    public User addNewUser(UserDTO userDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());  // Directly using the plain text password
        newUser.setIsAdmin(0); // Set default value for isAdmin (0 or 1 based on your logic)

        return userRepository.save(newUser);
    }

    // Method to find user by username and password (for login)
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
}
