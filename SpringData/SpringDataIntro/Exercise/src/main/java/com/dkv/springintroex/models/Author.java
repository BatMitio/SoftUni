package com.dkv.springintroex.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    private String firstName;
    private String lasName;
    private Set<Book> books;

    public Author() {
        super(null);
    }

    public Author(String firstName, String lasName) {
        super(null);
        this.firstName = firstName;
        this.lasName = lasName;
    }

    public Author(Long id, String firstName, String lasName) {
        super(id);
        this.firstName = firstName;
        this.lasName = lasName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLasName() {
        return lasName;
    }

    public void setLasName(String lasName) {
        this.lasName = lasName;
    }

    @OneToMany(mappedBy = "author", targetEntity = Book.class, fetch = FetchType.EAGER)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
