package springstudy.bookstore.repository;

import springstudy.bookstore.domain.dto.orderItem.GetOrderItemResponse;

import java.util.List;

public interface OrderItemRepositoryCustom {
  // 회원의 장바구니 아이디 번호로 장바구니에 담긴 주문상품 정보조회
  List<GetOrderItemResponse> findAllByCart_id(Long id);
}
