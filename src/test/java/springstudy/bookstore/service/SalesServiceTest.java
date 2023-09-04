package springstudy.bookstore.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.domain.dto.item.CreateItemRequest;
import springstudy.bookstore.domain.dto.user.CreateUserRequest;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.OrderItem;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Slf4j
@SpringBootTest
@Transactional
public class SalesServiceTest {

    @Autowired SalesService salesService;
    @Autowired UserService userService;
    @Autowired CartService cartService;


    List<MultipartFile> createMultipartFiles() {
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){ // 상품 이미지 경로 + 이미지 이름 저장해서 add
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    public User createUserTest() {
        CreateUserRequest dto = new CreateUserRequest();
        dto.setLoginId("nana20");
        dto.setPassword("1234@");
        dto.setName("faker");
        dto.setEmail("karis99@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("1120");

        userService.signUp(dto);
        return userService.findByLoginId(dto.getLoginId());
    }

    public User createCustomerTest() {
        CreateUserRequest dto = new CreateUserRequest();
        dto.setLoginId("wolf27");
        dto.setPassword("1234@");
        dto.setName("wolf");
        dto.setEmail("wolf1004@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("4015");

        userService.signUp(dto);
        return userService.findByLoginId(dto.getLoginId());
    }

    public CreateItemRequest createRequestItemDto(String sellerId) {
        CreateItemRequest dto = new CreateItemRequest();
        dto.setUploaderId(sellerId);
        dto.setItemName("테스트 상품명");
        dto.setCategoryType(CategoryType.BOOK);
        dto.setItemType(ItemType.BEST);
        dto.setPrice(10000);
        dto.setQuantity(100);
        return dto;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void uploadItem() throws Exception {
        // given : 회원 user 가 상품 등록을 하려고 한다.
        User user = createUserTest();

        // (등록할 상품 정보: 이미지파일, 상품 정보를 담은 dto)
        List<MultipartFile> multipartFiles = createMultipartFiles();
        CreateItemRequest dto = createRequestItemDto(user.getLoginId());

        // when : 상품 등록 로직을 실행했을 때
        salesService.uploadItem(user, dto, multipartFiles);

        // then : 상품이 잘 등록되었는지? 상품 정보를 확인해본다.
        assertEquals(dto.getItemName(), user.getSales().getItemList().get(0).getItemName());
        assertEquals(dto.getCategoryType(), user.getSales().getItemList().get(0).getCategoryType());
        log.info("dto info -> itemName={}", dto.getItemName());
        log.info("user uploaded the item->itemName={}", user.getSales().getItemList().get(0).getItemName());
    }


    @Test
    @DisplayName("구매요청을 받은 경우")
    void takeOrder() throws IOException {
        // given : 회원 Customer 주어졌을 때 장바구니에 상품을 10개 담으려고 한다.
        User seller = createUserTest();
        User buyer = createCustomerTest();

        // (등록할 상품 정보: 이미지파일, 상품 정보를 담은 dto)
        List<MultipartFile> multipartFiles = createMultipartFiles();
        CreateItemRequest dto = createRequestItemDto(seller.getLoginId());

        // when : 상품 등록 로직을 실행했을 때
        salesService.uploadItem(seller, dto, multipartFiles);
        Item item = seller.getSales().getItemList().get(0);

        // when : 상품 담기 로직 실행, 10개를 주문한다.
        int orderCount = 10;
        cartService.addWishList(buyer.getLoginId(), item.getId(), orderCount);


        //then : 구매자 결재금액과 판매자 판매액이 같은가?
        log.info("buyer -> order price ={}", buyer.getCart().getOrderItemList().get(0).getOrderPrice());
        log.info("seller -> total revenue ={}",  seller.getSales().getTotalRevenue());

        assertEquals(buyer.getCart().getOrderItemList().get(0).getOrderPrice(),  seller.getSales().getTotalRevenue());
    }


    @Test
    @DisplayName("장바구니에서 주문상품을 취소한다.")
    public void deleteWishList() throws IOException {
        // given : 회원 Customer 주어졌을 때 장바구니에 상품을 10개 담으려고 한다.
        User seller = createUserTest();
        User buyer = createCustomerTest();

        List<MultipartFile> multipartFiles = createMultipartFiles();
        CreateItemRequest dto = createRequestItemDto(seller.getLoginId());

        salesService.uploadItem(seller, dto,multipartFiles);

        // 구매자는 10개를 주문하려고 한다.
        Item item = seller.getSales().getItemList().get(0);
        int orderCount = 10;

        // when 1: 상품 담기 로직 실행, 10개를 주문한다.
        cartService.addWishList(buyer.getLoginId(), item.getId(), orderCount);

        // when 2: 구매자는 단순변심으로 장바구니를 삭제했다.
        log.info("buyer cart info = {}", buyer.getCart().getOrderItemList().get(0).getItem().getItemName());
        OrderItem orderItem = buyer.getCart().getOrderItemList().get(0);
        cartService.deleteWishList(orderItem.getId());
    }


}
