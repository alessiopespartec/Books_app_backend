package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

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

    public Author deleteAuthor(Long id) {
        Author authorToDelete = findAuthorById(id);
        authorRepository.delete(authorToDelete);
        return authorToDelete;
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author", id));
    }
}
