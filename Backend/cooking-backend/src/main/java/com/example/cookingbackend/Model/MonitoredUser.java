package com.example.cookingbackend.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MonitoredUser {

    @Id
    private Long userId;

    private LocalDateTime detectedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public MonitoredUser() {}

    public MonitoredUser(User user, LocalDateTime detectedAt) {
        this.user = user;
        this.userId = user.getId();
        this.detectedAt = detectedAt;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }
    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
