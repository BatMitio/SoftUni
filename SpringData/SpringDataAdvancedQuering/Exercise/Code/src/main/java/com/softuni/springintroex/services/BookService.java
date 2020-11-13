package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.AgeRestriction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BookService {
    void seed() throws IOException;
    List<String> getBooksTitlesWithAgeRestriction(AgeRestriction ageRestriction);
}
