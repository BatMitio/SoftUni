package com.dkv.springintroex.services.impl;

import com.dkv.springintroex.models.*;
import com.dkv.springintroex.repositories.BookRepository;
import com.dkv.springintroex.services.AuthorService;
import com.dkv.springintroex.services.BookService;
import com.dkv.springintroex.services.CategoryService;
import com.dkv.springintroex.utils.FileUtils;
import com.dkv.springintroex.utils.impl.FileUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepo;
    private AuthorService authorService;
    private CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepo, AuthorService authorService, CategoryService categoryService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seed() throws IOException {
        if(bookRepo.count() != 0) return;
        FileUtils fileUtils = new FileUtilsImpl();
        String[] content = fileUtils.readFileContent("src/main/resources/files/books.txt");
        Arrays.stream(content).forEach(b -> {
            String[] bookInfo = b.split("\\s+");
            int authorIndex = (new Random()).nextInt((int)(authorService.getAuthorsCount()));
            Author author = authorService.getAllAuthors().get(authorIndex);
            EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(bookInfo[1], formatter);

            int copies = Integer.parseInt(bookInfo[2]);
            BigDecimal price = new BigDecimal(bookInfo[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
            StringBuilder titleBuilder = new StringBuilder();

            for (int i = 5; i < bookInfo.length; i++) {
                titleBuilder.append(bookInfo[i]).append(" ");
            }

            String title = titleBuilder.toString().trim();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);


            Set<Category> categories = new LinkedHashSet<>();
            categories.add(categoryService.getAllCategories().get((new Random()).nextInt((int)categoryService.getCategoriesCounr())));
            book.setCategories(categories);

            bookRepo.saveAndFlush(book);

        });

    }

    @Override
    public List<Book> getBooksAfter(LocalDate after) {
        return bookRepo.findAllByReleaseDateAfter(after);
    }

    @Override
    public List<Book> getBooksFromAuthorNameOrdered(String authorName) {
        return bookRepo.findAllBooksByAuthorNameOrdered(authorName);
    }
}
