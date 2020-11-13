package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

//    @Query(value = "SELECT a FROM Author a " +
//            "JOIN Book as b ON b.author_id = a.id" +
//            "WHERE a.books.size > 0 AND b.release_date < '01/01/1990'", nativeQuery = true)
//    List<Author> getAllAuthorsWithBooksBefore1990();


    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> findAuthorsByCountOfBook();
}
