package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    @Query(value = "SELECT b FROM Book b WHERE concat(b.author.firstName, ' ' ,b.author.lastName) = 'George Powell'" +
            "ORDER BY b.releaseDate ASC, b.title ASC")
    List<Book> getAllBooksByGeorgePowell();

    List<Book> findAllByReleaseDateBefore(LocalDate localDate);
}
