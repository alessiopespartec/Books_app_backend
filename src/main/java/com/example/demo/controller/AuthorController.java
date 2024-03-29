package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import com.example.demo.exceptions.MessageFactory;
import com.example.demo.response.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("hasAuthority('SCOPE_authors_read')")
    @GetMapping
    public ResponseEntity<Object> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            throw new EntityNotFoundException("No authors found");
        }
        String successMessage = MessageFactory.successOperationMessage("Authors", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, authors);
    }

    @PreAuthorize("hasAuthority('SCOPE_authors_read')")
    @GetMapping("{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable Long id){
        Author author = authorService.getAuthor(id);
        String successMessage = MessageFactory.successOperationMessage("Author", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, author);
    }

    @PreAuthorize("hasAuthority('SCOPE_authors_create')")
    @PostMapping
    public ResponseEntity<Object> addAuthor(@Valid @RequestBody Author author) {
        Author authorCreated = authorService.addAuthor(author);
        String successMessage = MessageFactory.successOperationMessage("Author", "added");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.CREATED, authorCreated);
    }

    @PreAuthorize("hasAuthority('SCOPE_authors_update')")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@Valid @RequestBody Author author, @PathVariable Long id) {
        Author authorUpdated = authorService.updateAuthor(author, id);
        String successMessage = MessageFactory.successOperationMessage("Author", "updated");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, authorUpdated);
    }

    @PreAuthorize("hasAuthority('SCOPE_authors_delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        String successMessage = MessageFactory.successOperationMessage("Author", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }
}