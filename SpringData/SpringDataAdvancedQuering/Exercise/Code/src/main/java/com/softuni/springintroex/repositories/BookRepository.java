package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT COUNT(b.id) FROM Book b WHERE b.title = ?1")
    int doesBookExist(String title);

    @Query(value = "SELECT b.title FROM Book b WHERE lower(b.ageRestriction) = ?1")
    List<String> getBooksTitlesWithAgeRestriction(AgeRestriction ageRestriction);


}
