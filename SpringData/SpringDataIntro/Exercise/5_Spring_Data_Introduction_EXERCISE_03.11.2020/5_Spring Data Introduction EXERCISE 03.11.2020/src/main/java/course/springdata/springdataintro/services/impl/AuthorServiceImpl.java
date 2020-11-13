package course.springdata.springdataintro.services.impl;

import course.springdata.springdataintro.constants.Const;
import course.springdata.springdataintro.models.entities.Author;
import course.springdata.springdataintro.repositories.AuthorRepository;
import course.springdata.springdataintro.services.AuthorService;
import course.springdata.springdataintro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final FileUtil fileUtil;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        String[] fileContent = this.fileUtil.readFileContent(Const.AUTHORS_FILE_PATH);
        Arrays.stream(fileContent).forEach(authorFirstAndLastName -> {
            int delimiterIndex = authorFirstAndLastName.indexOf(" ");
            String firstName = authorFirstAndLastName.substring(0, delimiterIndex);
            String lastName = authorFirstAndLastName.substring(delimiterIndex + 1);
            if(this.authorRepository.findAll().stream()
                    .noneMatch(author -> author.getFirstName().equals(firstName) && author.getLastName().equals(lastName))) {
                Author author = new Author();
                author.setFirstName(firstName);
                author.setLastName(lastName);
                this.authorRepository.saveAndFlush(author);
            }
        });
    }

    @Override
    public int getAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(long id) {
        return this.authorRepository.findById(id).orElse(null);
    }
}