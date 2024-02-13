package com.example.demo.controller;

import com.example.demo.exceptions.MessageFactory;
import com.example.demo.entity.Publisher;
import com.example.demo.service.PublisherService;
import com.example.demo.response.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        if (publishers.isEmpty()) {
            throw new EntityNotFoundException("No publishers found");
        }
        String successMessage = MessageFactory.successOperationMessage("Publishers", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, publishers);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPublisher(@PathVariable Long id) {
        Publisher publisher = publisherService.getPublisher(id);
        String successMessage = MessageFactory.successOperationMessage("Publisher", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, publisher);
    }

    @PostMapping
    public ResponseEntity<Object> addPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher publisherCreated = publisherService.addPublisher(publisher);
        String successMessage = MessageFactory.successOperationMessage("Publisher", "added");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.CREATED, publisherCreated);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatePublisher(@Valid @RequestBody Publisher publisher, @PathVariable Long id) {
        Publisher publisherUpdated= publisherService.updatePublisher(publisher, id);
        String successMessage = MessageFactory.successOperationMessage("Publisher", "updated");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, publisherUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable Long id) {
        Publisher publisherDeleted = publisherService.deletePublisher(id);
        String successMessage = MessageFactory.successOperationMessage("Publisher", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, publisherDeleted);
    }
}
