package springstudy.bookstore.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springstudy.bookstore.controller.dto.ItemSortParam;
import springstudy.bookstore.domain.dto.ItemFormDto;
import springstudy.bookstore.domain.dto.MainItemDto;
import springstudy.bookstore.domain.dto.UserFormDto;
import springstudy.bookstore.domain.dto.UserMainItemDto;
import springstudy.bookstore.domain.dto.sort.ItemSearchCondition;
import springstudy.bookstore.domain.entity.Item;
import springstudy.bookstore.domain.entity.ItemImg;
import springstudy.bookstore.domain.entity.User;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.ItemType;
import springstudy.bookstore.repository.ItemImgRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemImgRepository imgRepository;
    @Autowired UserService userService;

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
        UserFormDto dto = new UserFormDto();
        dto.setLoginId("nana20");
        dto.setPassword("1234@");
        dto.setName("faker");
        dto.setEmail("karis99@naver.com");
        dto.setCity("서울");
        dto.setStreet("마포구 합정동");
        dto.setZipcode("1120");

        userService.signUp(dto);
        return userService.findOne(dto.getLoginId());
    }

    public ItemSearchCondition createSearchConditionTest() {
        return ItemSearchCondition.builder()
                .itemName("Anne")
                .loginMember(createUserTest())
                .build();
    }

    public ItemFormDto createItemFormDtoTest() {
        ItemFormDto dto = new ItemFormDto();
        dto.setItemName("테스트 상품명");
        dto.setCategoryType(CategoryType.BOOK);
        dto.setItemType(ItemType.BEST);
        dto.setPrice(10000);
        dto.setQuantity(100);
        dto.setStatus(ItemSellStatus.SELL);
        return dto;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void saveItem() throws Exception {
        // given : 회원 user 가 상품 등록을 하려고 한다.
        List<MultipartFile> multipartFiles = createMultipartFiles();
        User user = createUserTest();
        ItemFormDto dto = createItemFormDtoTest();

        // when : 상품 등록 로직을 실행했을 때
        Long itemId = itemService.saveItem_s3(user, dto, multipartFiles);


        List<ItemImg> itemImgList = imgRepository.findAllByItem_id(itemId);
        Item item = itemService.findById(itemId);

        // then : 상품이 잘 등록되었는지? 상품 정보를 확인해본다.
        assertEquals(dto.getItemName(), item.getItemName());
        assertEquals(dto.getCategoryType(), item.getCategoryType());
        assertEquals(multipartFiles.get(0).getOriginalFilename(), itemImgList.get(0).getOriginImgName());
    }


    @Test
    @DisplayName("상품 이름으로 검색조회 테스트")
    public void searchProduct() {
        // given : "Anne" 상품 이름을 검색하려고 한다.
        Pageable pageable = PageRequest.of(0, 10);
        Page<MainItemDto> result = itemService.searchPageSort(createSearchConditionTest(), pageable);

        // then : 상품 2개가 조회되는지 확인한다.
        assertEquals(2, result.getContent().size());
        for (MainItemDto mainItemDto : result) {
            System.out.println("mainItemDto.getItemName() = " + mainItemDto.getItemName());
        }
    }

    @Test
    @DisplayName("낮은 가격순 상품정렬")
    void ascSort_페이지테스트() {
        // given : 낮은 가격순으로 상품을 조회하려고 한다.
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemService.itemPriceSort(ItemSortParam.ASC.getCode(), pageable);
        List<MainItemDto> list = sort.getContent();

        // then : 가장 낮은 가격의 상품이 "Tara Duncan"이 맞는지 확인한다.
        assertThat(list.get(0).getImgName().equals("Tara Duncan"));
    }

    @Test
    @DisplayName("높은 가격순 상품정렬")
    void descSort_페이지테스트() {
        // given : 높은 가격순으로 상품을 조회하려고 한다.
        Pageable pageable = PageRequest.of(0, 4);
        Page<MainItemDto> sort = itemService.itemPriceSort(ItemSortParam.DESC.getCode(), pageable);
        List<MainItemDto> list = sort.getContent();

        // then : 가장 높은 가격의 상품이 "BAEK HYUN"이 맞는지 확인한다.
        assertThat(list.get(0).getImgName().equals("BAEK HYUN"));
    }

    @Test
    @DisplayName("사용자별 판매상품 리스트보기 테스트")
    void sortByUser_유저별판매제품테스트() {
        // given :  회원 "test4"가 판매하는 상품을 조회하려고 한다.
        User user = userService.findOne("test4");
        List<UserMainItemDto> itemDto = userService.findAllByUser(user);

        // then : 해당 회원이 판매하는 상품 개수가 13개가 맞는지?
        assertThat(itemDto.size() == 13);
        for (UserMainItemDto userMainItemDto : itemDto) {
            System.out.println("userMainItemDto.toString() = " + userMainItemDto.toString());
        }
    }

    @Test
    @DisplayName("카테고리별 상품페이지보기 테스트")
    void sortByCategoryType_카테고리정렬테스트() {
        // given : "BOOK"(책) 카테고리로 상품을 조회하려고 한다.
        Pageable pageable = PageRequest.of(0, 4);
        String code = CategoryType.BOOK.getTypeCode();

        // then :  로직을 실행
        Page<MainItemDto> sort = itemService.categoryPageSort(code, pageable);
        List<MainItemDto> content = sort.getContent();

        // then : 조회했을 때 해당 상품 카테고리 타입이 "BOOK"이 맞는지?
        assertThat(content.get(0).getCategoryType().getTypeCode().equals(CategoryType.BOOK.getTypeCode()));
        for (MainItemDto mainItemDto : content) {
            System.out.println(mainItemDto.toString());
        }
    }

    @Test
    @DisplayName("특정 카테고리의 상품 검색하기 테스트")
    void searchItemAndCategoryType_카테고리안에서검색테스트() {
        //given : "BOOK" 카테고리 페이지에서 상품 "Anne"을 검색하려고 한다.
        PageRequest pageable = PageRequest.of(0, 4);
        ItemSearchCondition condition = createSearchConditionTest();
        Page<MainItemDto> mainItemDtos = itemService.searchAndCategory(condition, CategoryType.BOOK.getTypeCode(), pageable);

        // then : 상품 2개가 조회 되는지?
        assertEquals(2, mainItemDtos.getContent().size());
        for (MainItemDto mainItemDto : mainItemDtos) {
            System.out.println("mainItemDto.toString() = " + mainItemDto.toString());
        }

    }
}