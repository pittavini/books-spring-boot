package ch.pittavini.booksws.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "book_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    private int version;

    @Column(name = "order_date")
    @ApiModelProperty(hidden = true)
    private Timestamp orderDate;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("title DESC")
    private SortedSet<Book> books = new TreeSet<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, date=%s}", getId(), getOrderDate().toString());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public SortedSet<Book> getBooks() {
        return books;
    }

    public void setBooks(SortedSet<Book> books) {
        this.books = books;
    }
}
