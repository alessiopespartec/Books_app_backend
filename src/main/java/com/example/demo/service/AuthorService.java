package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    private BookRepository bookRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthor(Long id) {
        return findAuthorById(id);
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author, Long id) {
        Author authorToUpdate = findAuthorById(id);

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());

        return authorRepository.save(authorToUpdate);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);

        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot delete author with associated books");
        }

        authorRepository.delete(author);
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Author", id));
    }
}
