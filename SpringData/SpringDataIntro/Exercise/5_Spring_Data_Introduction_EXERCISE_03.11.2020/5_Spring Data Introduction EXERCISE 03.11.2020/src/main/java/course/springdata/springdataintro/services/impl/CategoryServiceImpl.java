package course.springdata.springdataintro.services.impl;

import course.springdata.springdataintro.constants.Const;
import course.springdata.springdataintro.models.entities.Category;
import course.springdata.springdataintro.repositories.CategoryRepository;
import course.springdata.springdataintro.services.CategoryService;
import course.springdata.springdataintro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(FileUtil fileUtil, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        String[] fileContent = this.fileUtil.readFileContent(Const.CATEGORIES_FILE_PATH);
        Arrays.stream(fileContent).forEach(categoryName -> {
            if(this.categoryRepository.findAll().stream().noneMatch(category -> category.getName().equals(categoryName))) {
                Category category = new Category();
                category.setName(categoryName);
                this.categoryRepository.saveAndFlush(category);
            }
        });
    }

    @Override
    public Category findCategoryById(long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }
}