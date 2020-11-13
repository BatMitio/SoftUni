package com.softuni.springintroex.runner;

import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {
    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;
    private Scanner sc;

    @Autowired
    public AppRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
//        authorService.seed();
//        categoryService.seed();
//        bookService.seed();

        System.out.print("Please enter option 0-14 (0 is seeding the database) or type 'exit' to exit: ");
        String line = sc.nextLine();
        while (!line.equals("exit")){
            switch (line){
                case "0":
                    authorService.seed();
                    categoryService.seed();
                    bookService.seed();
                    break;
                case "1":
                    ex1BooksTitlesByAgeRestriction();
                    break;
            }

            System.out.print("Please enter option 0-14 (0 is seeding the database) or type 'exit' to exit: ");
            line = sc.nextLine();
        }

    }

    private void ex1BooksTitlesByAgeRestriction() {
        System.out.print("Please enter an age restriction: ");
        String ageRestriction = sc.nextLine().toUpperCase();
        List<String> titles = bookService.getBooksTitlesWithAgeRestriction(AgeRestriction.valueOf(ageRestriction));
        for (String title : titles) {
            System.out.println(title);
        }
    }

    private void ex2GoldenBooks(){

    }
}
