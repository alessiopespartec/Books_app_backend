package com.example.demo.service;

import com.example.demo.exceptions.EmailAlreadyExistsException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return findUserById(id);
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        User userToUpdate = findUserById(id);

        if (!user.getEmail().equals(userToUpdate.getEmail()) &&
            userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setProfilePhotoUrl(user.getProfilePhotoUrl());
        userToUpdate.setCountry(user.getCountry());
        userToUpdate.setOccupation(user.getOccupation());
        userToUpdate.setDateOfBirth(user.getDateOfBirth());
        userToUpdate.setGender(user.getGender());
        userToUpdate.setRole(user.getRole());

        return userRepository.save(userToUpdate);
    }

    public User deleteUser(Long id) {
        User userToDelete = findUserById(id);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @PostConstruct
    public void createDefaultUser() {
        if (userRepository.count() == 0) {
            User user = new User("admin@example.com", passwordEncoder.encode("password123"));
            userRepository.save(user);
        }
    }
}
