package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Publisher;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long bookId) {
        return findBookById(bookId);
    }

    @Transactional
    public Book addBook(Book book) {
        Publisher publisher = validateAndGetPublisher(book.getPublisher());
        book.setPublisher(publisher);

        Set<Author> authors = validateAndGetAuthors(book.getAuthors());
        book.setAuthors(authors);

        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Book book, Long bookId) {
        Publisher publisher = validateAndGetPublisher(book.getPublisher());
        Set<Author> authors = validateAndGetAuthors(book.getAuthors());

        Book bookToUpdate = findBookById(bookId);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setYear(book.getYear());
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setAuthors(authors);

        return bookRepository.save(bookToUpdate);
    }

    public Book deleteBook(Long id) {
        Book bookToDelete = findBookById(id);
        bookRepository.delete(bookToDelete);
        return bookToDelete;
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
    }

    private Publisher validateAndGetPublisher(Publisher publisher) {
        if (publisher == null || publisher.getId() == null) { // @NotBlank is not written in Book.java
            throw new IllegalArgumentException("Publisher must have an ID");
        }

        return publisherRepository.findById(publisher.getId())
                .orElseThrow(() -> new EntityNotFoundException("Publisher", publisher.getId()));
    }

    private Set<Author> validateAndGetAuthors(Set<Author> authors) {
        if (authors == null || authors.isEmpty()) { // @NotBlank is not written in Book.java
            throw new IllegalArgumentException("At least one author is required");
        }

        Set<Author> validatedAuthors = new HashSet<>();
        for (Author author : authors) {
            if (author.getId() == null) {
                throw new IllegalArgumentException("Each author must have an ID");
            }
            Author dbAuthor = authorRepository.findById(author.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Author", author.getId()));
            validatedAuthors.add(dbAuthor);
        }
        return validatedAuthors;
    }
}
