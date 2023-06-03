package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
