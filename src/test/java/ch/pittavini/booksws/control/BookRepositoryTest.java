package ch.pittavini.booksws.control;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import ch.pittavini.booksws.entity.Author;
import ch.pittavini.booksws.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@code @WebMvcTest} based tests for {@link BookRepository}.
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;

    @Test
    public void testFindBookByTitle_WithAuthor() {
        //given
        Author author = new Author();
        author.setName("James Wayne");
        Book book = getBook("title", Timestamp.valueOf(LocalDateTime.now()), new Author[]{author});

        bookRepo.saveAndFlush(book);

        //then
        List<Book> title = bookRepo.findAllByTitleContains("title");
        assertThat(title).hasOnlyOneElementSatisfying(abook -> {
            assertThat(abook).isEqualTo(book);
            SortedSet<Author> authors = abook.getAuthors();
            assertThat(authors).hasOnlyOneElementSatisfying(anAuthor -> {
                assertThat(anAuthor).isEqualTo(anAuthor);
            });
        });
    }


    @Test
    public void testFindBookByTitle_ContainingKeyword() {
        //given
        Author author = new Author();
        author.setName("James Wayne");
        Book book1 = getBook("title abc", Timestamp.valueOf(LocalDateTime.now()), new Author[]{author});
        Book book2 = getBook("title def", Timestamp.valueOf(LocalDateTime.now().minusMonths(1)), new Author[]{author});

        //when
        bookRepo.saveAndFlush(book1);
        bookRepo.saveAndFlush(book2);

        //then
        List<Book> title = bookRepo.findAllByTitleContains("title");
        assertThat(title).hasSameElementsAs(Arrays.asList(book1, book2));
    }

    @Test
    public void saveBookWithEmptyAuthors_GeneratesException() {
        //given
        Book book = getBook("batman", Timestamp.valueOf(LocalDateTime.now()), new Author[]{});

        assertThrows(ConstraintViolationException.class,
                ()-> {
                    //when
                    bookRepo.saveAndFlush(book);
                });
    }

    @Test
    public void saveBookWithNoPublicationDate_GeneratesException() {
        //given
        Book book = getBook("batman", null, new Author[]{});

        assertThrows(ConstraintViolationException.class,
                ()-> {
                    //when
                    bookRepo.saveAndFlush(book);
                });
    }

    @Test
    public void saveBookWithNoTitle_GeneratesException() {
        //given
        Book book = getBook(null, Timestamp.valueOf(LocalDateTime.now()), new Author[]{});

        assertThrows(ValidationException.class,
                ()-> {
                    //when
                    bookRepo.saveAndFlush(book);
                });
    }

    private Book getBook(String title, Timestamp publicationDate, Author[] authors) {
        Book book = new Book();
        book.setTitle(title);
        book.setPublicationDate(publicationDate);
        book.setAuthors(new TreeSet<>(Arrays.asList(authors)));
        return book;
    }
}