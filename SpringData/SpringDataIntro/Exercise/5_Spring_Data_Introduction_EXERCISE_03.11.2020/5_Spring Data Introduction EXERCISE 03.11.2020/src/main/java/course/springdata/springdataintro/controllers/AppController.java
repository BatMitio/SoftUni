package course.springdata.springdataintro.controllers;

import course.springdata.springdataintro.models.entities.Book;
import course.springdata.springdataintro.services.AuthorService;
import course.springdata.springdataintro.services.BookService;
import course.springdata.springdataintro.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
        //Ex 1
        List<Book> books = this.bookService.getAllBooksAfter2000();
        books.forEach(book -> System.out.printf("%s from %s %s", book.getTitle(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName()).println());
    }
}