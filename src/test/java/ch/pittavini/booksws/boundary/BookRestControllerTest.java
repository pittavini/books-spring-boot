package ch.pittavini.booksws.boundary;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TreeSet;

import ch.pittavini.booksws.entity.Author;
import ch.pittavini.booksws.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @WebMvcTest} based tests for {@link BookRestController}.
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerTest {

    private final String TITLE = "title test";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void addBookShouldReturnRespondCreated() throws Exception {
        //given
        Author author = new Author();
        author.setName("James Wayne");
        String notifJson = bookToJson(TITLE, Timestamp.valueOf(LocalDateTime.now()), new Author[]{author});

        //then
        this.mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notifJson)).andExpect(status().isCreated());
    }

    @Test
    void addBookShouldReturnBadRequest_EmptyAuthors() throws Exception {
        //given
        String notifJson = bookToJson(TITLE, Timestamp.valueOf(LocalDateTime.now()), new Author[]{});

        //then
        this.mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notifJson)).andExpect(status().isBadRequest());
    }

    @Test
    void addBookShouldReturnBadRequest_NullTitle() throws Exception {
        //given
        Author author = new Author();
        author.setName("James Wayne");
        String notifJson = bookToJson(null, Timestamp.valueOf(LocalDateTime.now()), new Author[]{author});

        //then
        this.mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notifJson)).andExpect(status().isBadRequest());
    }

    @Test
    void addBookShouldReturnBadRequest_NullPublicationDate() throws Exception {
        //given
        Author author = new Author();
        author.setName("James Wayne");
        String notifJson = bookToJson(TITLE, null, new Author[]{author});

        //then
        this.mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notifJson)).andExpect(status().isBadRequest());
    }

    private String bookToJson(String title, Timestamp publicationDate, Author[] authors)
            throws JsonProcessingException {
        Book book = new Book();
        book.setTitle(title);
        book.setPublicationDate(publicationDate);
        book.setAuthors(new TreeSet<>(Arrays.asList(authors)));
        return (new ObjectMapper()).writeValueAsString(book);
    }
}
