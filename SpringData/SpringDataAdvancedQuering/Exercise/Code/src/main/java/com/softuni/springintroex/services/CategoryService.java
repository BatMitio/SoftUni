package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CategoryService {
    void seed() throws IOException;
    List<Long> getIds();
    Category getById(Long id);
}
