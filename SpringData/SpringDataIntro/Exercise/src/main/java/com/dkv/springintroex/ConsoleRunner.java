package com.dkv.springintroex;

import com.dkv.springintroex.models.Author;
import com.dkv.springintroex.models.Book;
import com.dkv.springintroex.services.AuthorService;
import com.dkv.springintroex.services.BookService;
import com.dkv.springintroex.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Transactional
@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    public void seedDatabase() throws IOException {
        authorService.seed();
        categoryService.seed();
        bookService.seed();
    }

    @Override
    public void run(String... args) throws Exception {
        seedDatabase();

        bookService.getBooksAfter(LocalDate.of(2000, 01, 01)).stream().forEach(b -> {
            System.out.println(b.getTitle());
        });

//        List<Author> authors = authorService.getAuthorsWithBookBefore(LocalDate.of(1990, 01, 01));
//        for (Author author : authors) {
//            System.out.printf("%s %s %d%n", author.getFirstName(), author.getLasName(), author.getBooks().size());
//        }

//        List<Author> authors = authorService.getAllAuthorsOrdered();
//        for (Author author : authors) {
//            System.out.printf("%s %s %d%n", author.getFirstName(), author.getLasName(), author.getBooks().size());
//        }

//        List<Book> books = bookService.getBooksFromAuthorNameOrdered("George Powell");
//        for (Book book : books) {
//            System.out.printf("%s %s %d%n", book.getTitle(), book.getReleaseDate(), book.getCopies());
//        }

    }
}
