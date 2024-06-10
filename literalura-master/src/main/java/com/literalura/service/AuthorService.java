package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAliveAuthorsInYear(int year) {
        return authorRepository.findByBirthDateBeforeAndDeathDateAfter(
                new Date(year - 1900, 1, 1), // Date constructor is deprecated, this is just an example
                new Date(year - 1900, 12, 31)
        );
    }

    public Author findOrCreateAuthor(String name) {
        return authorRepository.findByName(name)
                .orElseGet(() -> authorRepository.save(new Author(name)));
    }
}
