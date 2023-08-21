package springstudy.bookstore.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springstudy.bookstore.domain.entity.*;
import springstudy.bookstore.domain.enums.CategoryType;
import springstudy.bookstore.domain.enums.IsMainImg;
import springstudy.bookstore.domain.enums.ItemSellStatus;
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

            em.persist(user);
            sellBook(user);
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

            em.persist(user);
            sellMusic(user);
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
                    .user(userB)
                    .itemName("Ariana Grande")
                    .price(12000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            Item item2 = Item.initItemBuilder()
                    .user(userB)
                    .itemName("BAEK HYUN")
                    .price(30000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img1 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Ariana Grande.jpg")
                    .savePath("c:save/Ariana Grande.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item1)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("BAEK HYUN.jpg")
                    .savePath("c:save/BAEK HYUN.jpg")
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
                    .user(user)
                    .itemName("1984")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("1984.jpg")
                    .savePath("c:save/1984.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item)
                    .build();

            em.persist(item);
            em.persist(img);


            Item item2 = Item.initItemBuilder()
                    .user(user)
                    .itemName("And Then There Were None")
                    .price(8000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("And Then There Were None.jpg")
                    .savePath("c:save/And Then There Were None.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item2)
                    .build();

            em.persist(item2);
            em.persist(img2);


            Item item3 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Anne of Green Gables")
                    .price(7900)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img3 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Anne of Green Gables.jpg")
                    .savePath("c:save/Anne of Green Gables.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item3)
                    .build();

            em.persist(item3);
            em.persist(img3);

            Item item4 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Damian")
                    .price(7700)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img4 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Damian.jpg")
                    .savePath("c:save/Damian.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item4)
                    .build();

            em.persist(item4);
            em.persist(img4);

            Item item5 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Little Women")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img5 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Little Women.jpg")
                    .savePath("c:save/Little Women.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item5)
                    .build();

            em.persist(item5);
            em.persist(img5);

            Item item6 = Item.initItemBuilder()
                    .user(user)
                    .itemName("MOMO")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img6 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("MOMO.jpg")
                    .savePath("c:save/MOMO.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item6)
                    .build();

            em.persist(item6);
            em.persist(img6);

            Item item7 = Item.initItemBuilder()
                    .user(user)
                    .itemName("My Sweet Orange Tree")
                    .price(5000)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img7 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("My Sweet Orange Tree.jpg")
                    .savePath("c:save/My Sweet Orange Tree.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item7)
                    .build();

            em.persist(item7);
            em.persist(img7);


            Item item8 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Peter Pan")
                    .price(4900)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img8 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Peter Pan.jpg")
                    .savePath("c:save/Peter Pan.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item8)
                    .build();
            em.persist(item8);
            em.persist(img8);

            Item item9 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Seven Years of Darkness")
                    .price(12000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img9 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Seven Years of Darkness.jpg")
                    .savePath("c:save/Seven Years of Darkness.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item9)
                    .build();

            em.persist(item9);
            em.persist(img9);

            Item item10 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Tara Duncan")
                    .price(1000)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img10 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Tara Duncan.jpg")
                    .savePath("c:save/Tara Duncan.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item10)
                    .build();

            em.persist(item10);
            em.persist(img10);


            Item item11 = Item.initItemBuilder()
                    .user(user)
                    .itemName("The Blue Bird for Children")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img11 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("The Blue Bird for Children.jpg")
                    .savePath("c:save/The Blue Bird for Children.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item11)
                    .build();

            em.persist(item11);
            em.persist(img11);

            Item item12 = Item.initItemBuilder()
                    .user(user)
                    .itemName("The Diary of Anne Frank")
                    .price(20000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.BOOK)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img12 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("The Diary of Anne Frank.jpg")
                    .savePath("c:save/The Diary of Anne Frank.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item12)
                    .build();
            em.persist(item12);
            em.persist(img12);
        }

        private void sellMusic(User user) {
            Item item = Item.initItemBuilder()
                    .user(user)
                    .itemName("Edith Piaf")
                    .price(14200)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Edith Piaf.jpg")
                    .savePath("c:save/Edith Piaf.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item)
                    .build();

            em.persist(item);
            em.persist(img);

            Item item2 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Warner Classics")
                    .price(13300)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img2 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Warner Classics.jpg")
                    .savePath("c:save/Warner Classics.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item2)
                    .build();
            em.persist(item2);
            em.persist(img2);

            Item item3 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Utopia")
                    .price(10300)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img3 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Utopia.jpg")
                    .savePath("c:save/Utopia.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item3)
                    .build();

            em.persist(item3);
            em.persist(img3);

            Item item4 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Utada Hikaru")
                    .price(10100)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img4 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Utada Hikaru.jpg")
                    .savePath("c:save/Utada Hikaru.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item4)
                    .build();

            em.persist(item4);
            em.persist(img4);

            Item item5 = Item.initItemBuilder()
                    .user(user)
                    .itemName("totoro")
                    .price(10190)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img5 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("totoro.jpg")
                    .savePath("c:save/totoro.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item5)
                    .build();

            em.persist(item5);
            em.persist(img5);

            Item item6 = Item.initItemBuilder()
                    .user(user)
                    .itemName("sm")
                    .price(8810)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img6 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("sm.jpg")
                    .savePath("c:save/sm.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item6)
                    .build();

            em.persist(item6);
            em.persist(img6);

            Item item7 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Sia")
                    .price(7710)
                    .stockQuantity(10)
                    .itemType(ItemType.HIGHEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img7 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Sia.jpg")
                    .savePath("c:save/Sia.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item7)
                    .build();

            em.persist(item7);
            em.persist(img7);

            Item item8 = Item.initItemBuilder()
                    .user(user)
                    .itemName("pororo")
                    .price(10090)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img8 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("pororo.jpg")
                    .savePath("c:save/pororo.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item8)
                    .build();

            em.persist(item8);
            em.persist(img8);

            Item item9 = Item.initItemBuilder()
                    .user(user)
                    .itemName("NewJeans")
                    .price(8930)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img9 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("NewJeans.jpg")
                    .savePath("c:save/NewJeans.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item9)
                    .build();

            em.persist(item9);
            em.persist(img9);


            Item item10 = Item.initItemBuilder()
                    .user(user)
                    .itemName("Miriam Makeba")
                    .price(7710)
                    .stockQuantity(10)
                    .itemType(ItemType.BEST)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img10 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("Miriam Makeba.jpg")
                    .savePath("c:save/Miriam Makeba.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item10)
                    .build();
            em.persist(item10);
            em.persist(img10);

            Item item11 = Item.initItemBuilder()
                    .user(user)
                    .itemName("La La Land OST by Justin Hurwitz")
                    .price(10000)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img11 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("La La Land OST by Justin Hurwitz.jpg")
                    .savePath("c:save/La La Land OST by Justin Hurwitz.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item11)
                    .build();
            em.persist(item11);
            em.persist(img11);

            Item item12 = Item.initItemBuilder()
                    .user(user)
                    .itemName("John Coltrane")
                    .price(10150)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img12 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("John Coltrane.jpg")
                    .savePath("c:save/John Coltrane.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item12)
                    .build();
            em.persist(item12);
            em.persist(img12);

            Item item13 = Item.initItemBuilder()
                    .user(user)
                    .itemName("BLACKPINK")
                    .price(16100)
                    .stockQuantity(10)
                    .itemType(ItemType.LOWER)
                    .categoryType(CategoryType.MUSIC)
                    .status(ItemSellStatus.SELL)
                    .build();

            ItemImg img13 = ItemImg.imgBuilder()
                    .originImgName("origin.jpg")
                    .imgName("BLACKPINK.jpg")
                    .savePath("c:save/BLACKPINK.jpg")
                    .isMainImg(IsMainImg.Y)
                    .item(item13)
                    .build();
            em.persist(item13);
            em.persist(img13);
        }
    }

}
