package com.dkv.springintroex.repositories;

import com.dkv.springintroex.models.Author;
import com.dkv.springintroex.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT DISTINCT a FROM Author a " +
            "JOIN Book as b ON b.author.id = a.id " +
            "WHERE a.books.size > 0 AND b.releaseDate < ?1")
    List<Author> getAllAuthorsWithBooksBefore(LocalDate before);


    @Query(value = "SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> getAllAuthorsOrdered();


}
