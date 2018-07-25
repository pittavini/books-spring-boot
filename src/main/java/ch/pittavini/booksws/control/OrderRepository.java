package ch.pittavini.booksws.control;

import java.util.List;

import ch.pittavini.booksws.entity.Book;
import ch.pittavini.booksws.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
