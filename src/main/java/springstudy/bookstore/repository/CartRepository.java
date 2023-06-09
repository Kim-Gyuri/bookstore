package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
