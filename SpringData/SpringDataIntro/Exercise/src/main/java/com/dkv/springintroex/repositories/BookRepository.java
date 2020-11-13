package com.dkv.springintroex.repositories;

import com.dkv.springintroex.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate after);

    @Query(value = "SELECT b FROM Book b " +
            "WHERE concat(b.author.firstName, ' ', b.author.lasName) = ?1 " +
            "ORDER BY b.releaseDate DESC, b.title ASC ")
    List<Book> findAllBooksByAuthorNameOrdered(String name);

}
