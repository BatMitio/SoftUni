package course.springdata.springdataintro.repositories;

import course.springdata.springdataintro.models.entities.Author;
import course.springdata.springdataintro.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);
    List<Book> findAllByAuthorEqualsOrderByReleaseDateDescTitleAsc(Author author);
}