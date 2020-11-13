package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.repositories.AuthorRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    final private FileUtil fileUtil;
    final private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(FileUtil fileUtil, AuthorRepository authorRepository) {
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seed() throws IOException {
        String[] authors = fileUtil.readFileContent(GlobalConstants.AUTHORS_FILE_PATH);
        Arrays.stream(authors).forEach(a -> {
            String[] authorInfo = a.split("\\s+");
            if(authorRepository.doesAuthorExist(authorInfo[0], authorInfo[1]) == 0){
                Author author = new Author(authorInfo[0], authorInfo[1]);
                authorRepository.saveAndFlush(author);
            }
        });
    }

    @Override
    public Set<Long> getIds() {
        return authorRepository.getIds();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
}
