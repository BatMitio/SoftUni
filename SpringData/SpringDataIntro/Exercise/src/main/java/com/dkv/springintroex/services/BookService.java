package com.dkv.springintroex.services;

import com.dkv.springintroex.models.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seed() throws IOException;
    List<Book> getBooksAfter(LocalDate after);
    List<Book> getBooksFromAuthorNameOrdered(String authorName);
}
