package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    // New method to find user by username
    Optional<User> findByUsername(String username);
}
