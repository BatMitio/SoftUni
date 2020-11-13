package course.springdata.springdataintro.services;

import course.springdata.springdataintro.models.entities.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;
    int getAuthorsCount();
    Author findAuthorById(long id);
}