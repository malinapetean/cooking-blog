package com.example.cookingbackend.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ActionLog() {}

    public ActionLog(String action, LocalDateTime timestamp, User user) {
        this.action = action;
        this.timestamp = timestamp;
        this.user = user;
    }

    // Getters and setters
}

