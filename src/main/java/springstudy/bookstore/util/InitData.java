package springstudy.bookstore.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.entity.*;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.domain.enums.ItemType;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;


    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Address addressA = Address.addressBuilder()
                    .city("진주")
                    .street("2")
                    .zipcode("2222")
                    .build();

            User user = User.userBuilder()
                    .loginId("test3")
                    .password("test3!")
                    .name("userC")
                    .email("karis99@naver.com")
                    .address(addressA)
                    .build();

            sellBook(user);
            em.persist(user);
        }

        public void dbInit2() {

            Address addressA = Address.addressBuilder()
                    .city("진주")
                    .street("2")
                    .zipcode("2222")
                    .build();

            User user = User.userBuilder()
                    .loginId("test4")
                    .password("test4!")
                    .name("userD")
                    .email("bambi05@naver.com")
                    .address(addressA)
                    .build();

            sellMusic(user);
            em.persist(user);
        }

        public void dbInit3(){

            Address addressA = Address.addressBuilder()
                    .city("진주")
                    .street("2")
                    .zipcode("2222")
                    .build();

            User userA = User.userBuilder()
                    .loginId("test")
                    .password("test!")
                    .name("userA")
                    .email("mimi05@naver.com")
                    .address(addressA)
                    .build();

            Address addressB = Address.addressBuilder()
                    .city("서울")
                    .street("1")
                    .zipcode("1111")
                    .build();

            User userB = User.userBuilder()
                    .loginId("test2")
                    .password("test2!")
                    .name("userB")
                    .email("nana10@naver.com")
                    .address(addressA)
                    .build();

            em.persist(userA);
            em.persist(userB);

            Item item1 = Item.initItemBuilder()
                    .sellerId(userB.getLoginId())
                    .itemName("Ariana Grande")
                    .price(12000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            Item item2 = Item.initItemBuilder()
                    .sellerId(userB.getLoginId())
                    .itemName("BAEK HYUN")
                    .price(30000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img1 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Ariana Grande.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/3bd66ffe-53fe-4e7f-b676-4909ccddbb4b")
                    .isMainImg(IsMainImg.Y)
                    .item(item1)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("BAEK HYUN.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/27392311-6b6c-4852-8087-6a12834b9908")
                    .isMainImg(IsMainImg.Y)
                    .item(item2)
                    .build();

            em.persist(item1);
            em.persist(img1);
            em.persist(item2);
            em.persist(img2);

            OrderItem orderItem1 = OrderItem.orderItemBuilder()
                    .cart(userA.getCart())
                    .item(item1)
                    .count(5)
                    .build();

            OrderItem orderItem2 = OrderItem.orderItemBuilder()
                    .cart(userA.getCart())
                    .item(item2)
                    .count(2)
                    .build();

            userA.addCartItem(orderItem1);
            userA.addCartItem(orderItem2);
        }

        private void sellBook(User user) {
            Item item = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("1984")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("1984.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/c87b0c45-1ac6-41c3-beb2-82cd80d8c7fd")
                    .isMainImg(IsMainImg.Y)
                    .item(item)
                    .build();

            em.persist(item);
            em.persist(img);


            Item item2 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("And Then There Were None")
                    .price(8000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("And Then There Were None.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/62c68662-fd5a-4e15-90a3-7279d99c94cf")
                    .isMainImg(IsMainImg.Y)
                    .item(item2)
                    .build();

            em.persist(item2);
            em.persist(img2);


            Item item3 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Anne of Green Gables")
                    .price(7900)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img3 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Anne of Green Gables.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/2e564f8b-4367-4acf-99bf-77e56958991c")
                    .isMainImg(IsMainImg.Y)
                    .item(item3)
                    .build();

            em.persist(item3);
            em.persist(img3);

            Item item4 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Damian")
                    .price(7700)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img4 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Damian.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/d679e445-39d9-43cd-8b83-9af46df52d1f")
                    .isMainImg(IsMainImg.Y)
                    .item(item4)
                    .build();

            em.persist(item4);
            em.persist(img4);

            Item item5 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Little Women")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img5 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Little Women.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/082cbfb9-deaa-479e-8fb3-87b561509012")
                    .isMainImg(IsMainImg.Y)
                    .item(item5)
                    .build();

            em.persist(item5);
            em.persist(img5);

            Item item6 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("MOMO")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img6 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("MOMO.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/e0bd1c01-d9aa-41a7-9ae2-f56f761195cd")
                    .isMainImg(IsMainImg.Y)
                    .item(item6)
                    .build();

            em.persist(item6);
            em.persist(img6);

            Item item7 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("My Sweet Orange Tree")
                    .price(5000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img7 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("My Sweet Orange Tree.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5f7fac5-f1e5-4f34-92a2-c5f52c820403")
                    .isMainImg(IsMainImg.Y)
                    .item(item7)
                    .build();

            em.persist(item7);
            em.persist(img7);


            Item item8 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Peter Pan")
                    .price(4900)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img8 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Peter Pan.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/19e5bc6b-3eda-4dab-ac6e-c799ebbdbb6c")
                    .isMainImg(IsMainImg.Y)
                    .item(item8)
                    .build();
            em.persist(item8);
            em.persist(img8);

            Item item9 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Seven Years of Darkness")
                    .price(12000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img9 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Seven Years of Darkness.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/d39ca436-a351-4e91-92bf-2f5b55eb047b")
                    .isMainImg(IsMainImg.Y)
                    .item(item9)
                    .build();

            em.persist(item9);
            em.persist(img9);

            Item item10 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Tara Duncan")
                    .price(1000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img10 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Tara Duncan.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/145237d6-b511-4dcd-947a-492589fdbee8")
                    .isMainImg(IsMainImg.Y)
                    .item(item10)
                    .build();

            em.persist(item10);
            em.persist(img10);


            Item item11 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("The Blue Bird for Children")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img11 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("The Blue Bird for Children.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/effeec6a-1e61-4e99-87db-b28fba714c08")
                    .isMainImg(IsMainImg.Y)
                    .item(item11)
                    .build();

            em.persist(item11);
            em.persist(img11);

            Item item12 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("The Diary of Anne Frank")
                    .price(20000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.BOOK)
                    .build();

            ItemImg img12 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("The Diary of Anne Frank.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/f704fd45-7e33-48d9-a5f3-cc729132b85b")
                    .isMainImg(IsMainImg.Y)
                    .item(item12)
                    .build();
            em.persist(item12);
            em.persist(img12);
        }

        private void sellMusic(User user) {
            Item item = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Edith Piaf")
                    .price(14200)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Edith Piaf.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/08498786-065d-472f-96f4-bab7b019141e")
                    .isMainImg(IsMainImg.Y)
                    .item(item)
                    .build();

            em.persist(item);
            em.persist(img);

            Item item2 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Warner Classics")
                    .price(13300)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Warner Classics.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5ddb6fa-cf02-4208-b615-ff3eefcbf5ca")
                    .isMainImg(IsMainImg.Y)
                    .item(item2)
                    .build();
            em.persist(item2);
            em.persist(img2);

            Item item3 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Utopia")
                    .price(10300)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img3 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Utopia.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/2aa0fec5-2e03-4eb2-aeb3-635874fa56c0")
                    .isMainImg(IsMainImg.Y)
                    .item(item3)
                    .build();

            em.persist(item3);
            em.persist(img3);

            Item item4 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Utada Hikaru")
                    .price(10100)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img4 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Utada Hikaru.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/3a0df0b4-ca73-49c7-9b59-10270cb1ad9d")
                    .isMainImg(IsMainImg.Y)
                    .item(item4)
                    .build();

            em.persist(item4);
            em.persist(img4);

            Item item5 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("totoro")
                    .price(10190)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img5 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("totoro.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/62a76cee-a563-4934-be0e-06b72903ee44")
                    .isMainImg(IsMainImg.Y)
                    .item(item5)
                    .build();

            em.persist(item5);
            em.persist(img5);

            Item item6 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("sm")
                    .price(8810)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img6 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("sm.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/d141de99-308c-4585-b02d-6e27fd8c5351")
                    .isMainImg(IsMainImg.Y)
                    .item(item6)
                    .build();

            em.persist(item6);
            em.persist(img6);

            Item item7 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Sia")
                    .price(7710)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img7 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Sia.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/edd76fca-4377-4de1-bb91-db87c5ecd845")
                    .isMainImg(IsMainImg.Y)
                    .item(item7)
                    .build();

            em.persist(item7);
            em.persist(img7);

            Item item8 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("pororo")
                    .price(10090)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img8 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("pororo.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/410b31ce-21a0-4041-88d0-3d391d90ac70")
                    .isMainImg(IsMainImg.Y)
                    .item(item8)
                    .build();

            em.persist(item8);
            em.persist(img8);

            Item item9 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("NewJeans")
                    .price(8930)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img9 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("NewJeans.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/11cc1176-7a3b-4206-b698-5e137f93ef49")
                    .isMainImg(IsMainImg.Y)
                    .item(item9)
                    .build();

            em.persist(item9);
            em.persist(img9);


            Item item10 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("Miriam Makeba")
                    .price(7710)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img10 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Miriam Makeba.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/2c6bad42-7a57-456e-8cab-8c42e32b10f8")
                    .isMainImg(IsMainImg.Y)
                    .item(item10)
                    .build();
            em.persist(item10);
            em.persist(img10);

            Item item11 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("La La Land OST by Justin Hurwitz")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img11 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("La La Land OST by Justin Hurwitz.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/27d1f3a8-0a07-472c-be4f-9ff59a33a9a7")
                    .isMainImg(IsMainImg.Y)
                    .item(item11)
                    .build();
            em.persist(item11);
            em.persist(img11);

            Item item12 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("John Coltrane")
                    .price(10150)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img12 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("John Coltrane.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/9981c6e7-f3f2-428b-bf16-bd289ce8fd0a")
                    .isMainImg(IsMainImg.Y)
                    .item(item12)
                    .build();
            em.persist(item12);
            em.persist(img12);

            Item item13 = Item.initItemBuilder()
                    .sellerId(user.getLoginId())
                    .itemName("BLACKPINK")
                    .price(16100)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .build();

            ItemImg img13 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("BLACKPINK.jpg")
                    .savePath("https://github.com/Kim-Gyuri/bookstore/assets/57389368/e6d6777f-7e4f-49d0-9d30-ed59202610c9")
                    .isMainImg(IsMainImg.Y)
                    .item(item13)
                    .build();
            em.persist(item13);
            em.persist(img13);
        }
    }

}
