package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Category;
import com.softuni.springintroex.repositories.CategoryRepository;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    final private FileUtil fileUtil;
    final private CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(FileUtil fileUtil, CategoryRepository categoryRepo) {
        this.fileUtil = fileUtil;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void seed() throws IOException {
        String[] categories = fileUtil.readFileContent(GlobalConstants.CATEGORIES_FILE_PATH);
        Arrays.stream(categories).forEach(c -> {
            String[] categoryInfo = c.split("\\s+");
            if(categoryRepo.doesCategoryExist(categoryInfo[0]) == 0){
                Category category = new Category(categoryInfo[0]);
                categoryRepo.saveAndFlush(category);
            }
        });
    }

    @Override
    public List<Long> getIds() {
        return categoryRepo.getIds();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }


}
