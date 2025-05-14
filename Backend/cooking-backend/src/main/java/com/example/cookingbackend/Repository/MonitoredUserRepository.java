package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.MonitoredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredUserRepository extends JpaRepository<MonitoredUser, Long> {
    boolean existsByUserId(Long userId);
}
