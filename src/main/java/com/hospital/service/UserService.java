package com.hospital.service;

import com.hospital.exceptions.DuplicatePasswordException;
import com.hospital.model.User;
import com.hospital.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User save(User user) {
        Optional<User> existing = repo.findByPassword(user.getPassword());

        if (user.getId() == null) {
            // Creating new user
            if (existing.isPresent()) {
                throw new DuplicatePasswordException("Password already in use by another user");
            }
        } else {
            // Updating existing user
            if (existing.isPresent() && !existing.get().getId().equals(user.getId())) {
                throw new DuplicatePasswordException("Password already in use by another user");
            }
        }

        return repo.save(user);
    }


    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
