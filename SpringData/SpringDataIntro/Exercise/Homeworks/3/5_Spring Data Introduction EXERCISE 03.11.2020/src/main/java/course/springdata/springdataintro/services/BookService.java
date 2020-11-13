package course.springdata.springdataintro.services;

import course.springdata.springdataintro.models.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
    List<Book> getAllBooksWithAuthor();
}