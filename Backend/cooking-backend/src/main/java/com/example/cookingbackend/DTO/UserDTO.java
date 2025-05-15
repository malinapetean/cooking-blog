package com.example.cookingbackend.DTO;

public class UserDTO {
    private String username;
    private String password;

    // Default constructor
    public UserDTO() {}

    // Constructor with fields
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
