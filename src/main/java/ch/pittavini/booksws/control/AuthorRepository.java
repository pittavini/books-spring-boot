package ch.pittavini.booksws.control;

import java.util.List;

import ch.pittavini.booksws.entity.Author;
import ch.pittavini.booksws.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
