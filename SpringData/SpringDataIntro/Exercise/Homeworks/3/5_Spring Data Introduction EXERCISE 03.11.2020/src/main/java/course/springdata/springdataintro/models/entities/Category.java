package course.springdata.springdataintro.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private Set<Book> books;

    public Category() { }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @ManyToMany(mappedBy = "categories", targetEntity = Book.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Book> getBooks() { return books; }

    public void setBooks(Set<Book> books) { this.books = books; }
}