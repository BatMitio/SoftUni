package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT COUNT(c.id) FROM Category c WHERE c.name = ?1")
    int doesCategoryExist(String name);

    @Query(value = "SELECT c.id FROM Category c")
    List<Long> getIds();
}
