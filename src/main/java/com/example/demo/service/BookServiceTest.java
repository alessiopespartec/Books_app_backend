package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, publisherRepository, authorRepository);
    }

    @Test
    void testAddBook() {
        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Author author = new Author();
        author.setId(1L);

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Book book = new Book();
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setTitle("Test Book");
        book.setYear(LocalDate.of(2020, 1, 1));

        // Verify that findById returns an Optional
        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        bookService.addBook(book);
        // Verify that bookService.addBook is saving a book in database
        verify(bookRepository).save(any(Book.class));
    }
}
