package com.softuni.springintroex.controllers;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner scanner;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        System.out.println("Please insert a number from 1 to 4 to run exercise or type End to terminate the program");
        System.out.println();
        String command = scanner.nextLine();

        while (!command.equalsIgnoreCase("End")) {
            try {
                int number = Integer.parseInt(command);
                switch (number) {
                    case 1:
                        List<Book> books = this.bookService.getAllBooksAfter2000();
                        for (Book book : books) {
                            System.out.println(book.getTitle());
                        }
                        break;
                    case 2:
                        List<Book> books2 = this.bookService.getAllBooksBefore1990();
                        for (Book book : books2) {
                            System.out.println(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
                        }
                        break;
                    case 3:
                        List<Author> authors = this.authorService.findAllAuthorsByCountOfBooks();
                        for (Author author : authors) {
                            System.out.printf("%s %s %s%n", author.getFirstName(), author.getLastName(), author.getBooks().size());
                        }
                        break;
                    case 4:
                        List<Book> books3 = this.bookService.getAllBooksByGeorgePowell();
                        for (Book book : books3) {
                            System.out.printf("%s %s %d%n", book.getTitle(), book.getReleaseDate().toString(), book.getCopies());
                        }
                        break;
                    default:
                        System.out.println("Invalid exercise number");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input format");
            }

            System.out.println();
            System.out.println("Please insert a number from 1 to 4 to run exercise or type End to terminate the program");
            System.out.println();
            command = scanner.nextLine();
        }
        System.out.println("Thank you! Goodbye!");

    }
}
