package ch.pittavini.booksws.entity;

import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;


@Entity
public class Author implements Comparable<Author> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    private int version;

    private String name;

    @ManyToMany(mappedBy = "authors")
    @OrderBy("title ASC")
    private SortedSet<Book> books = new TreeSet<>();

    @Override
    public int compareTo(Author o) {
        return name.compareTo(o.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<Book> getBooks() {
        return books;
    }

    public void setBooks(SortedSet<Book> books) {
        this.books = books;
    }
}