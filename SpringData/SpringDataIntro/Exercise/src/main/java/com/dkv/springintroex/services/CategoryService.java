package com.dkv.springintroex.services;

import com.dkv.springintroex.models.Category;
import com.dkv.springintroex.repositories.CategoryRepository;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seed() throws IOException;
    List<Category> getAllCategories();

    int getCategoriesCounr();
}
