package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
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
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('SCOPE_books_read')")
    @GetMapping
    public ResponseEntity<Object> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            throw new EntityNotFoundException("No books found");
        }
        String successMessage = MessageFactory.successOperationMessage("Books", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, books);
    }

    @PreAuthorize("hasAuthority('SCOPE_books_read')")
    @GetMapping("{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        String successMessage = MessageFactory.successOperationMessage("Book", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, book);
    }

    @PreAuthorize("hasAuthority('SCOPE_books_create')")
    @PostMapping
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book book) {
        Book bookCreated = bookService.addBook(book);
        String successMessage = MessageFactory.successOperationMessage("Book", "added");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.CREATED, bookCreated);
    }

    @PreAuthorize("hasAuthority('SCOPE_books_update')")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody Book book, @PathVariable Long id) {
        Book bookUpdated = bookService.updateBook(book, id);
        String successMessage = MessageFactory.successOperationMessage("Book", "updated");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, bookUpdated);
    }

    @PreAuthorize("hasAuthority('SCOPE_books_delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        String successMessage = MessageFactory.successOperationMessage("Book", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }
}
