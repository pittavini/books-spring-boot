package ch.pittavini.booksws.control;

import java.util.List;

import ch.pittavini.booksws.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List findAllByTitleContains(String title);
}
