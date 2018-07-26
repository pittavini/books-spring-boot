package ch.pittavini.booksws.boundary;

import java.util.List;
import javax.validation.Valid;

import ch.pittavini.booksws.control.BookRepository;
import ch.pittavini.booksws.entity.Book;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation(value = "Create a book",
            notes = "Add a book to the collection")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Book book) {
        bookRepository.save(book);
    }

    /**
     * The RequestBody annotation for parameter book is only used to keep swagger happy entering json
     * as a request, otherwise the application does not need it
     */
    @ApiOperation(value = "Delete a book",
            notes = "Deleted a book entry, only users with admin role can perform this operation")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Returns a list of books based on sorting field, direction is set to Desc
     *
     * @param sortField the sort field
     * @return a list of books when successful othewise bad request
     */
    @ApiOperation(value = "Find all books",
            notes = "Retrieves the list of books based on the provided sort field, direction is fixed to desc.  List count is limited to 20 entries.  Default sort field is book publication date")
    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List findAll(@RequestParam(value = "sort_field", required=false) String sortField) {
        String sortFieldValue = sortField == null ? "publicationDate" : sortField;
        final PageRequest page = PageRequest.of(0, 20, Sort.Direction.DESC, sortFieldValue);
        return bookRepository.findAll(page).getContent();
    }
}
