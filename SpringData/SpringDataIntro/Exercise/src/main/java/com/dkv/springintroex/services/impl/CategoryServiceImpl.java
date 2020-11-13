package com.dkv.springintroex.services.impl;

import com.dkv.springintroex.models.Author;
import com.dkv.springintroex.models.Category;
import com.dkv.springintroex.repositories.CategoryRepository;
import com.dkv.springintroex.services.CategoryService;
import com.dkv.springintroex.utils.FileUtils;
import com.dkv.springintroex.utils.impl.FileUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepo;

    @Autowired
    public void setCategoryRepo(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void seed() throws IOException {
        FileUtils fileUtils = new FileUtilsImpl();
        String[] content = fileUtils.readFileContent("src/main/resources/files/categories.txt");
        Arrays.stream(content).forEach(c -> {
            String[] categoryInfo = c.split("\\s+");
            Category category = new Category();
            category.setName(categoryInfo[0]);
            if(categoryRepo.findAll().stream().noneMatch(currentAuthor -> currentAuthor.getName().equals(category.getName()))){
                categoryRepo.saveAndFlush(category);
            }
        });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public int getCategoriesCounr() {
        return (int) this.categoryRepo.count();
    }
}
