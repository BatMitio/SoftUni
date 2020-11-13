package com.dkv.springintroex.services;

import com.dkv.springintroex.models.Author;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface AuthorService {
    void seed() throws IOException;
    List<Author> getAuthorsWithBookBefore(LocalDate before);
    List<Author> getAllAuthorsOrdered();
    List<Author> getAllAuthors();

    int getAuthorsCount();
}
