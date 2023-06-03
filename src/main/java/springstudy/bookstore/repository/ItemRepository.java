package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Item findByItemName(String itemName);
    List<Item> findAllByUser(User user);
}
