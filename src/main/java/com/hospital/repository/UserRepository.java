package com.hospital.repository;

import com.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Add this method to check if a password already exists
    Optional<User> findByPassword(String password);
}
