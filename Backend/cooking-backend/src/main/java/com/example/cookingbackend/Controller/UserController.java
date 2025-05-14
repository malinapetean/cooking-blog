package com.example.cookingbackend.Controller;

import com.example.cookingbackend.DTO.UserDTO;
import com.example.cookingbackend.Model.User;
import com.example.cookingbackend.Repository.MonitoredUserRepository;
import com.example.cookingbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MonitoredUserRepository monitoredUserRepository;

    // Endpoint to register a new user (signup)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addNewUser(userDTO);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to login using UserDTO (username and password)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        Optional<User> user = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getIsAdmin().toString());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
    }



}
