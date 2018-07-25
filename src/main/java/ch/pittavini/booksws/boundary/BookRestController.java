package ch.pittavini.booksws.boundary;

import java.util.List;
import javax.validation.Valid;

import ch.pittavini.booksws.control.BookRepository;
import ch.pittavini.booksws.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * The RequestBody annotation for parameter book is only used to keep swagger happy entering json
     * as a request, otherwise the application does not need it
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Book book) {
        bookRepository.save(book);
    }

    /**
     * The RequestBody annotation for parameter book is only used to keep swagger happy entering json
     * as a request, otherwise the application does not need it
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        bookRepository.deleteById(id);
    }

    /**
     * The RequestBody annotation for parameter book is only used to keep swagger happy entering json
     * as a request, otherwise the application does not need it
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List find(@PathVariable String title) {
        return bookRepository.findAllByTitleContains(title);
    }
}
