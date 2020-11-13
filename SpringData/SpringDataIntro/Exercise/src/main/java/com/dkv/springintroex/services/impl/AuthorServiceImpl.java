package com.dkv.springintroex.services.impl;

import com.dkv.springintroex.models.Author;
import com.dkv.springintroex.repositories.AuthorRepository;
import com.dkv.springintroex.services.AuthorService;
import com.dkv.springintroex.utils.FileUtils;
import com.dkv.springintroex.utils.impl.FileUtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepo;

    @Autowired
    public void setAuthorRepo(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public void seed() throws IOException {
        FileUtils fileUtils = new FileUtilsImpl();
        String[] content = fileUtils.readFileContent("src/main/resources/files/authors.txt");
        Arrays.stream(content).forEach(a -> {
            String[] authorInfo = a.split("\\s+");
            Author author = new Author(authorInfo[0], authorInfo[1]);
            if(authorRepo.findAll().stream().noneMatch(currentAuthor -> currentAuthor.getFirstName().equals(author.getFirstName()) &&
                    currentAuthor.getLasName().equals(author.getLasName()))){
            authorRepo.saveAndFlush(author);
            }
        });
    }

    @Override
    public List<Author> getAuthorsWithBookBefore(LocalDate before) {
        return authorRepo.getAllAuthorsWithBooksBefore(before);
    }

    @Override
    public List<Author> getAllAuthorsOrdered() {
        return authorRepo.getAllAuthorsOrdered();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public int getAuthorsCount() {
        return (int) this.authorRepo.count();
    }
}
