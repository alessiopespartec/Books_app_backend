package com.example.demo.controller;

import com.example.demo.exceptions.MessageFactory;
import com.example.demo.model.User;
import com.example.demo.response.ResponseHandler;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }
        String successMessage = MessageFactory.successOperationMessage("Users", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, users);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        String successMessage = MessageFactory.successOperationMessage("User", "retireved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, user);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        String successMessage = MessageFactory.successOperationMessage("User", "added");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.CREATED, createdUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.updateUser(user, id);
        String successMessage = MessageFactory.successOperationMessage("User", "updated");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, updatedUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        User userDeleted = userService.deleteUser(id);
        String successMessage = MessageFactory.successOperationMessage("User", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, userDeleted);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllUsers() {
        userService.deleteAll();
        String successMessage = MessageFactory.successOperationMessage("Users", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }
}
