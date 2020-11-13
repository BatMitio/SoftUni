package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public interface AuthorService {
    void seed() throws IOException;
    Set<Long> getIds();
    Author getById(Long id);
}
