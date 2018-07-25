package ch.pittavini.booksws.control;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.PrePersist;

import ch.pittavini.booksws.entity.Book;

public class BookPersistListener {

    @PrePersist
    void preSave(Book book) {
    }
}
