package springstudy.bookstore.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.service.UserService;

import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class OrderItemRepositoryTest {

    @Autowired OrderItemRepository orderItemRepository;
    @Autowired UserService userService;

    @Test
    @DisplayName("나의 장바구니 상품 조회")
    void findAllByCartId() {
        //init 데이터 확인해보기 -> 회원 ID "test"의 장바구니 사움을 출력해보기
        User user = userService.findByLoginId("test");

        List<GetOrderItemResponse> dtos = orderItemRepository.findAllByCart_id(user.getCart().getId());
        for (GetOrderItemResponse dto : dtos) {
            log.info("test 회원님의 상품조회={}", dto.toString());
        }
    }
    @Test
    @DisplayName("나의 판매 상품 조회")
    void findAllBySellerId() {
        //init 데이터 확인해보기 -> 회원 ID "test"의 장바구니 사움을 출력해보기
        User user = userService.findByLoginId("test2");

        //OrderItem orderItem = user.getSales().getOrderItemList().get(0);
        //log.info("tt={}", orderItem.toString());
        List<GetOrderItemResponse> result = orderItemRepository.findAllBySellerId(user.getLoginId());
        log.info("orderItem -> {}", result.get(0).getName());
        log.info("orderItem -> {}", result.get(1).getName());

        for (GetOrderItemResponse getOrderItemResponse : result) {
            log.info("테스트중", getOrderItemResponse.getName());
        }
    }
}
