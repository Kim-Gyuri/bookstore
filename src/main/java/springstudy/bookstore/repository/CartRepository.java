package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Cart;
import springstudy.bookstore.domain.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
