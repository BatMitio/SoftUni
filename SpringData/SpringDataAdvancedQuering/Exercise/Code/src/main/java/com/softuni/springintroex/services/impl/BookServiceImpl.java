package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.repositories.BookRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    final private BookRepository bookRepo;
    final private AuthorService authorService;
    final private CategoryService categoryService;
    final private FileUtil fileUtil;
    final private Random random = new Random();

    @Autowired
    public BookServiceImpl(BookRepository bookRepo, AuthorService authorService, CategoryService categoryService, FileUtil fileUtil) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seed() throws IOException {
        String[] books = fileUtil.readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Arrays.stream(books).forEach(b -> {
            String[] data = b.split("\\s+");

            Set<Long> authorsIds = authorService.getIds();
            Long authorId = (Long)authorsIds.toArray()[random.nextInt(authorsIds.size())];
            Author author = authorService.getById(authorId);
            EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(data[1], formatter);
            int copies = Integer.parseInt(data[2]);
            BigDecimal price = new BigDecimal(data[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }

            String title = titleBuilder.toString().trim();

//            if(bookRepo.doesBookExist(title) == 0) {
                Book book = new Book();
                book.setAuthor(author);
                book.setEditionType(editionType);
                book.setReleaseDate(releaseDate);
                book.setCopies(copies);
                book.setPrice(price);
                book.setAgeRestriction(ageRestriction);
                book.setTitle(title);

                List<Long> categoriesIds = categoryService.getIds();
                Set<Category> categoriesToAdd = new LinkedHashSet<>();
                for(int i = 0; i < Math.min(categoriesIds.size(), 3); ++i){
                    Category categoryToAdd = categoryService.getById(categoriesIds.get(random.nextInt(categoriesIds.size())));
                    if(categoriesToAdd.contains(categoryToAdd)){
                        --i;
                        continue;
                    }
                    categoriesToAdd.add(categoryToAdd);
                }
                book.setCategories(categoriesToAdd);

                bookRepo.save(book);
//            }
        });
    }

    @Override
    public List<String> getBooksTitlesWithAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepo.getBooksTitlesWithAgeRestriction(ageRestriction);
    }
}
