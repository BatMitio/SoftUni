package course.springdata.springdataintro.services;

import course.springdata.springdataintro.models.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    Category findCategoryById(long id);
}