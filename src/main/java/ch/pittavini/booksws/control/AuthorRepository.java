package ch.pittavini.booksws.control;

import ch.pittavini.booksws.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
