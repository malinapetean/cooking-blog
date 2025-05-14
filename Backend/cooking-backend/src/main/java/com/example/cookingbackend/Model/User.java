package com.example.cookingbackend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // Specify the table name as "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing ID
    private Long id;

    @Column(unique = true, nullable = false)  // Ensure username is unique and not null
    private String username;

    @Column(nullable = false)  // Password cannot be null
    private String password;

    @Column(name = "is_admin")  // Renaming column to "is_admin" to match the database column
    private Integer isAdmin;

    // Default constructor
    public User() {}

    // Constructor with fields
    public User(String username, String password, Integer isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
