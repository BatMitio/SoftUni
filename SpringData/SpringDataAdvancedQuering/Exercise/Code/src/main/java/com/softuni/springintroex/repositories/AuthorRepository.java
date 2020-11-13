package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT COUNT(a.id) FROM Author a " +
            "WHERE a.firstName = ?1 AND a.lastName = ?2 ")
    int doesAuthorExist(String firstName, String lastName);

    @Query(value = "SELECT a.id FROM Author a")
    Set<Long> getIds();
}
