package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {

}

