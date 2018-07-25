package ch.pittavini.booksws.entity;

import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ch.pittavini.booksws.control.BookPersistListener;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "book")
@EntityListeners(BookPersistListener.class)
public class Book implements Comparable<Book> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    private int version;

    @Column(name = "publication_date")
    @ApiModelProperty(hidden = true)
    @NotNull
    private Timestamp publicationDate;

    @NotBlank
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
            joinColumns = { @JoinColumn(name = "fk_book") },
            inverseJoinColumns = { @JoinColumn(name = "fk_author") })
    @OrderBy("name ASC")
    @NotEmpty
    private SortedSet<Author> authors = new TreeSet<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title=%s}", getId(), getTitle());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Timestamp getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }

    public SortedSet<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(SortedSet<Author> authors) {
        this.authors = authors;
    }

    @Override
    public int compareTo(Book book) {
        return title.compareTo(book.getTitle());
    }
}
