package course.springdata.springdataintro.services.impl;

import course.springdata.springdataintro.constants.Const;
import course.springdata.springdataintro.constants.enumerations.AgeRestriction;
import course.springdata.springdataintro.constants.enumerations.EditionType;
import course.springdata.springdataintro.models.entities.Author;
import course.springdata.springdataintro.models.entities.Book;
import course.springdata.springdataintro.models.entities.Category;
import course.springdata.springdataintro.repositories.BookRepository;
import course.springdata.springdataintro.services.AuthorService;
import course.springdata.springdataintro.services.BookService;
import course.springdata.springdataintro.services.CategoryService;
import course.springdata.springdataintro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private final FileUtil fileUtil;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(FileUtil fileUtil, BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.fileUtil = fileUtil;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if(this.bookRepository.count() != 0) return;
        String[] fileContent = this.fileUtil.readFileContent(Const.BOOKS_FILE_PATH);
        Arrays.stream(fileContent).forEach(bookInfo -> {
            String[] params = bookInfo.split("\\s+");
            int authorsCount = this.authorService.getAuthorsCount();
            int authorIndex = new Random().nextInt(authorsCount) + 1;
            Author author = this.authorService.findAuthorById(authorIndex);
            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);
            int copies = Integer.parseInt(params[2]);
            BigDecimal price = new BigDecimal(params[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < params.length; i++) titleBuilder.append(params[i]).append(" ");
            String title = titleBuilder.toString().trim();
            Set<Category> categories = this.getRandomCategories();
            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(categories);
            this.bookRepository.saveAndFlush(book);
        });
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("31/12/2000", formatter);
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();
        int bound = new Random().nextInt(3) + 1;
        for (int i = 1; i <= bound; i++) {
            int categoryId = new Random().nextInt(8) + 1;
            if(result.stream().anyMatch(category -> category.getId() == categoryId)) continue;
            result.add(this.categoryService.findCategoryById(categoryId));
        }
        return  result;
    }
}