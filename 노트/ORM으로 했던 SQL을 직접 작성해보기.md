> SQL을 직접 작성하려고 했던 이유

프로젝트 만들 때 JPA를 사용했기 때문에 실제 쿼리를 작성해보지 않았었다. <br> 그래서 "등록된 상품 관련 조회" 쿼리를 SQL로 직접 작성해보면서 정리해보려고 한다. <br> <br>

### 도메인 모델
> 도메인 설계는 [2) 도메인 모델과 테이블 설계](https://thumper.tistory.com/42)편에 자세히 정리했었다.

간단히 정리하자면 아래와 같다.
+ 회원은 구매와 판매 모두 가능하다.
+ 회원 상품을 판매하기 위해 상품 정보, 이미지 첨부를 해야 한다.
+ 구매 시 여러 종류의 상품을 선택하여 장바구니에 담아 구매할 수 있다.


### 데이터베이스 테이블 설계
> 요구사항을 기반으로 설계한 테이블 ERD다.

![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/363caf7c-6713-4d47-9885-5ef756697b96) <br><br>    
> (프로젝트는 항상 뒤돌아서 생각해보면, 아쉬운 부분이나 개선해야 할 코드부분이 잘 보인다.) <br>
> (이번에는 ERD 설계가 아쉬운 것 같아서, "user-sales-item" "user-cart-order_item"으로 판매권한, 구매권한 역할이 잘 보이도록 개선할 예정이다.)

<br>

### 조회 SQL을 작성하기 위한 데이터 입력
중고 거래 사이트에는 회원 "userA" "userB" "userC" "userD" 이 있다고 가정하자. <br>
+ 회원 UserA, userC, userD는 상품을 팔기 위해서 업로드했다.
+ 회원 userB는 userA의 상품을 구매하기 위해 장바구니에 담았다.

<details>
<summary>입력할 데이터</summary>

회원 "userA"가 등록한 상품은 아래와 같다. <br>
> (id = 55, cart_id = 56로 설정했다.) <br>

`음반` <br>
```
Utada Hikaru
ToToRo
GG
Sia
PoRoRo
NewJeans
Miriam Makeba
La La Land OST by Justin Hurwitz
John Coltrane
BLACKPINK
Ariana Grande
BAEK HYUN
```

<br><br>

회원 "userB"는 상품을 팔지 않았지만, "userA"의 판매상품을 장바구니에 담았다. <br>
> (id = 57, cart_id = 58로 설정했다.) <br>
```
회원 userA가 판매하는 상품을 샀다.
상품명[Ariana Grande]  : 5개
상품명[BAEK HYUN] : 2개
```

<br><br>

회원 "userC"가 등록한 상품은 아래와 같다. <br>
> (id = 1, cart_id = 2로 설정했다.) <br>

`책` <br>
```
1984, 
And Then There Were None, 
Anne of Green Gables,
Damian
Little Women
MOMO
My Sweet Orange Tree
Peter Pan
```

<br><br>

회원 "userD"가 등록한 상품은 아래와 같다. <br>
> (id = 27, cart_id = 28로 설정했다.) <br>

`책` <br>
```
Seven Years of Darkness
Tara Duncan
The Blue Bird for Children
The Diary of Anne Frank
```

`음반` <br>
```
Edith Piaf
Warner Classics
Utopia
```

</details>

<br><br>

### 테이블 생성을 위한 SQL 작성
장바구니(cart), 상품(item), 상품에 필요한 이미지(item_img), order_item(주문한 상품), users(회원) 테이블이 필요하다. <br>
```
create table cart (cart_id bigint not null, primary key (cart_id));

create table item (item_id bigint not null, category_type varchar(255), item_name varchar(255), item_type varchar(255), price integer, status varchar(255), stock_quantity integer, user_user_id bigint, primary key (item_id));

create table item_img (item_img_id bigint not null, img_name varchar(255), is_main_img integer, origin_img_name varchar(255), save_path varchar(255), item_id bigint, primary key (item_img_id));

create table order_item (order_item_id bigint not null, count integer, order_price integer, cart_id bigint, item_id bigint, primary key (order_item_id));

create table users (user_id bigint not null, city varchar(255), street varchar(255), zipcode varchar(255), email varchar(255), login_id varchar(255), name varchar(255), password varchar(255), cart_id bigint, primary key (user_id))
```

### 테이블 기본키 PK 설정
SQL Server에서 기본키(primary key), FK(foreign Key)를 생성하기 위해 "ALTER TABLE"문을 사용하여 제약조건을 만든다. <br> 
```
ALTER TABLE "테이블명" ADD CONSTRAINT "PK 제약조건명" PRIMARY KEY ("PK 컬럼명") REFERENCES "참조할 테이블명"
```
> REPERENCES : 참조할 부모 테이블/참조할 컬럼을 정의한다.

아래와 같이 SQL을 작성하면 된다. <br>
```
alter table item add constraint FKnqm6ql58o1j8klhh5d31q82pg foreign key (user_user_id) references users;
alter table item_img add constraint FKdd5u08y3ap4c46ayrqjf8g88m foreign key (item_id) references item;
alter table order_item add constraint FKkgu3wv2n7r2shg2wbvc4nsu7l foreign key (cart_id) references cart;
alter table order_item add constraint FKija6hjjiit8dprnmvtvgdp6ru foreign key (item_id) references item;
alter table users add constraint FKqmifheg6lnigfifvlmpjnuny8 foreign key (cart_id) references cart;
```

<br><br>

### 값 입력 SQL 작성
다음과 같이 작성하면 된다.
```
insert into 테이블명(컬럼명...) values(값...);
```

<details>
<summary>cart 테이블 값 세팅</summary>
  
```
insert into cart(cart_id) values(2);
insert into cart(cart_id) values(28);
insert into cart(cart_id) values(56);
insert into cart(cart_id) values(58);
```

</details>

<details>
<summary> user 테이블 값 세팅</summary>

```
insert into users (user_id, city, street, zipcode, email, login_id, name, password, cart_id)
values (1, '진주', '2', '2222', 'karis99@naver.com', 'test3', 'userC', 'test3!', 2);

insert into users (user_id, city, street, zipcode, email, login_id, name, password, cart_id)
values (27, '서울', '3', '3333', 'bambi05@naver.com', 'test4', 'userD', 'test4!', 28);

insert into users (user_id, city, street, zipcode, email, login_id, name, password, cart_id)
values (55, '광주', '4', '4444', 'mimi00@naver.com', 'test', 'userA', 'test!', 56);

insert into users (user_id, city, street, zipcode, email, login_id, name, password, cart_id)
values (57, '부천', '5', '5555', 'nana10@naver.com', 'test2', 'userB', 'test2!', 58);
```

</details>


<details>
<summary>item 테이블 값 세팅</summary>

```
# 회원 "userC"가 등록한 상품
insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (3, 'BOOK', '1984', 'HIGHEST', 10000, 'SELL', 10, 1)

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (5, 'BOOK', 'And Then There Were None', 'HIGHEST', 8000, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (7, 'BOOK', 'Anne of Green Gables', 'HIGHEST', 7900, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (9, 'BOOK', 'Damian', 'HIGHEST', 7700, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (11, 'BOOK', 'Little Women', 'HIGHEST', 11000, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (13, 'BOOK', 'MOMO', 'HIGHEST', 10000, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (15, 'BOOK', 'My Sweet Orange Tree', 'HIGHEST', 10000, 'SELL', 10, 1);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (17, 'BOOK', 'Peter Pan', 'HIGHEST', 5000, 'SELL', 10, 1);



# 회원 "userD"가 등록한 상품
insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (19, 'BOOK', 'Seven Years of Darkness', 'BEST', 5000, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (21, 'BOOK', 'Tara Duncan', 'BEST', 3000, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (23, 'BOOK', 'The Blue Bird for Children', 'LOWER', 1200, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (25, 'BOOK', 'The Diary of Anne Frank', 'LOWER', 6100, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (29, 'MUSIC', 'Edith Piaf', 'HIGHEST', 14200, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (31, 'MUSIC', 'Warner Classics', 'HIGHEST', 13300, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (33, 'MUSIC', 'Utopia', 'HIGHEST', 10300, 'SELL', 10, 27);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (35, 'MUSIC', 'Utada Hikaru', 'HIGHEST', 18000, 'SELL', 10, 55);



# 회원 "userA"가 등록한 상품
insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (37, 'MUSIC', 'ToToRo', 'HIGHEST', 3000, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (39, 'MUSIC', 'GG', 'HIGHEST', 5000, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (41, 'MUSIC', 'Sia', 'HIGHEST', 7710, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (43, 'MUSIC', 'PoRoRo', 'BEST', 9680, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (45, 'MUSIC', 'NewJeans', 'BEST', 8040, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (47, 'MUSIC', 'Miriam Makeba', 'BEST', 6690, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (49, 'MUSIC', 'La La Land OST by Justin Hurwitz', 'LOWER', 30000, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (51, 'MUSIC', 'John Coltrane', 'LOWER', 8340, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (53, 'MUSIC', 'BLACKPINK', 'LOWER', 21400, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (59, 'MUSIC', 'Ariana Grande', 'BEST', 9940, 'SELL', 10, 55);

insert into item (item_id, category_type, item_name, item_type, price, status, stock_quantity, user_user_id)
values (61, 'MUSIC', 'BAEK HYUN', 'BEST', 30000, 'SELL', 10, 55);
```

</details>


<details>
<summary>item_img 테이블 값 세팅</summary>

```
insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (4, '1984', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/c87b0c45-1ac6-41c3-beb2-82cd80d8c7fd', 3);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (6, 'And Then There Were None', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/62c68662-fd5a-4e15-90a3-7279d99c94cf', 5);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (8, 'Anne of Green Gables', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/2e564f8b-4367-4acf-99bf-77e56958991c',7);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (10, 'Damian', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/d679e445-39d9-43cd-8b83-9af46df52d1f', 9);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (12, 'Little Women', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/082cbfb9-deaa-479e-8fb3-87b561509012', 11);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (14, 'MOMO', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/e0bd1c01-d9aa-41a7-9ae2-f56f761195cd', 13);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (16, 'My Sweet Orange Tree', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5f7fac5-f1e5-4f34-92a2-c5f52c820403', 15);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (18, 'Peter Pan', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/19e5bc6b-3eda-4dab-ac6e-c799ebbdbb6c', 17);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (20, 'Seven Years of Darkness', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/d39ca436-a351-4e91-92bf-2f5b55eb047b', 19);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (22, 'Tara Duncan', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/145237d6-b511-4dcd-947a-492589fdbee8', 21);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (24, 'The Blue Bird for Children', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/effeec6a-1e61-4e99-87db-b28fba714c08', 23);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (26, 'The Diary of Anne Frank', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/f704fd45-7e33-48d9-a5f3-cc729132b85b', 25);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (30, 'Edith Piaf', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/08498786-065d-472f-96f4-bab7b019141e', 29);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (32, 'Warner Classics', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5ddb6fa-cf02-4208-b615-ff3eefcbf5ca', 33);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (34, 'Utopia', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/2aa0fec5-2e03-4eb2-aeb3-635874fa56c0', 33);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (36, 'Utada Hikaru', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/da6f0ef1-e2a7-4c7c-bfe0-0702ff82c680', 35)

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (38, 'ToToRo', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/62a76cee-a563-4934-be0e-06b72903ee44', 37);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (40, 'GG', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/d141de99-308c-4585-b02d-6e27fd8c5351', 39);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (42, 'Sia', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/edd76fca-4377-4de1-bb91-db87c5ecd845', 41);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (44, 'PoRoRo', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/410b31ce-21a0-4041-88d0-3d391d90ac70', 43);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (46, 'NewJeans', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/11cc1176-7a3b-4206-b698-5e137f93ef49', 45);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (48, 'Miriam Makeba', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/2c6bad42-7a57-456e-8cab-8c42e32b10f8', 47);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (50, 'La La Land OST by Justin Hurwitz', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/27d1f3a8-0a07-472c-be4f-9ff59a33a9a7', 49);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (52, 'John Coltrane', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/9981c6e7-f3f2-428b-bf16-bd289ce8fd0a', 51);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (54, 'BLACKPINK', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/e6d6777f-7e4f-49d0-9d30-ed59202610c9', 53);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (60, 'Ariana Grande',  0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/3bd66ffe-53fe-4e7f-b676-4909ccddbb4b', 59);

insert into item_img (item_img_id, img_name, is_main_img, origin_img_name, save_path, item_id)
values (62, 'BAEK HYUN', 0, 'origin', 'https://github.com/Kim-Gyuri/bookstore/assets/57389368/27392311-6b6c-4852-8087-6a12834b9908', 61);
```

</details>


<details>
<summary>order_item 테이블 값 세팅</summary>

```
insert into order_item (order_item_id, count, order_price, cart_id, item_id) values (63, 5, 49700, 58, 59);

insert into order_item (order_item_id, count, order_price, cart_id, item_id) values (64, 2, 60000, 58, 61);
```

</details>

<br><br>

### 생성된 테이블 
위의 데이터 값으로 insert 쿼리를 실행했다면 아래아 같이 테이블이 완성된다. <br>
#### CART 테이블 
![cart 테이블](https://github.com/Kim-Gyuri/bookstore/assets/57389368/c8c75ff2-c1fb-4fb6-9b63-c9a5b20c3a4c) <br><br>
#### ITEM 테이블 <br> 
![item 테이블](https://github.com/Kim-Gyuri/bookstore/assets/57389368/18c0f897-1181-4d8d-8811-83405353a5ef) <br><br>
#### ITEM_IMG 테이블 <br> 
![Item_Img 테이블](https://github.com/Kim-Gyuri/bookstore/assets/57389368/12dda357-1298-4da9-8db3-e31be8e9bd24) <br><br>
#### ORDER_ITEM 테이블 <br> 
![order_item 테이블](https://github.com/Kim-Gyuri/bookstore/assets/57389368/78d54afb-86e3-4cdf-a530-e396e57b3fc0) <br><br>
#### USER 테이블 <br> 
![user 테이블](https://github.com/Kim-Gyuri/bookstore/assets/57389368/d6be2fef-2a95-4967-811c-6129ee979bb2)


<br><br>

### 조회 SQL 작성
#### 회원ID로 해당 회원이 등록한 상품을 조회한다.
회원ID 1인 회원이 등록한 상품을 조회하기 위한 쿼리는 다음과 같다.
```
SELECT ITEM.ITEM_NAME, ITEM.PRICE, ITEM.STOCK_QUANTITY, ITEM_IMG.SAVE_PATH, ITEM.ITEM_TYPE, ITEM.CATEGORY_TYPE 
FROM ITEM
INNER JOIN ITEM_IMG ON ITEM.ITEM_ID = ITEM_IMG.ITEM_ID
INNER JOIN USERS ON ITEM.USER_USER_ID = USERS.USER_ID
WHERE USERS.USER_ID = 1;
```
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/4648d5cc-9d51-4074-9bc0-d6f11baa958a)

<br>

#### 카테고리 타입으로 조회
상품 카테고리 타입이 "음반"인 상품을 조회하는 쿼리는 다음과 같다.
```
SELECT ITEM.ITEM_NAME, ITEM.PRICE, ITEM.STOCK_QUANTITY, ITEM_IMG.SAVE_PATH, ITEM.ITEM_TYPE, ITEM.CATEGORY_TYPE 
FROM ITEM
INNER JOIN ITEM_IMG ON ITEM.ITEM_ID = ITEM_IMG.ITEM_ID
WHERE ITEM.CATEGORY_TYPE = 'MUSIC';
```
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/9773d4bb-87ce-4b56-aeaf-e72cd72c2fda)

<br>

#### 카테고리 타입, 상품 이름으로 조회
상품 카테고리 타입이 "음반"이면서 상품 이름에 "Anne"이 들어가는 상품을 조회하는 쿼리는 다음과 같다.
```
SELECT ITEM.ITEM_NAME, ITEM.PRICE, ITEM.STOCK_QUANTITY, ITEM_IMG.SAVE_PATH, ITEM.ITEM_TYPE, ITEM.CATEGORY_TYPE 
FROM ITEM
INNER JOIN ITEM_IMG ON ITEM.ITEM_ID = ITEM_IMG.ITEM_ID
WHERE ITEM_NAME LIKE '%Anne%' AND CATEGORY_TYPE = 'BOOK';
```
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/dabc0827-8282-4b61-9390-b3e8bf935ab8)

<br>

####  상품 가격 내림차순 조회
상품 가격 기준으로 내림차순 정렬하여 상품을 조회하는 쿼리는 다음과 같다.
```
SELECT ITEM.ITEM_NAME, ITEM.PRICE, ITEM.STOCK_QUANTITY, ITEM_IMG.SAVE_PATH, ITEM.ITEM_TYPE, ITEM.CATEGORY_TYPE 
FROM ITEM
INNER JOIN ITEM_IMG ON ITEM.ITEM_ID = ITEM_IMG.ITEM_ID
INNER JOIN USERS ON ITEM.USER_USER_ID = USERS.USER_ID
ORDER BY PRICE DESC;
```
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/a17fea24-ea78-4f95-b4b3-7a47cc9f36b2)

<br><br>

### 후기
SQL 문제풀이가 아닌 프로젝트에 대한 SQL을 직접 작성해보려고 하니까, <br> 
평소에 쿼리처리를 ORM으로 해결해버리려는 자신에 대해 한번 더 생각해볼 시간을 가질 수 있었다. <br>
간단하게 조회 쿼리를 작성해보면서 SQL 기초 정리에 대해 필요성을 더 느끼게 되었다. <br>
다음 글로 "SQL 기초 정리"편을 작성해보면서 다음과 같은 내용을 정리해보려고 한다.
+  SELECT - FROM - WHERE - GROUP BY - HAVING - ORDER BY 순서로 작성해보기
+  조인 (INNER JOIN, LEFT JOIN, RIGHT JOIN ...)

#### 추천하는 참고자료 (블로그 글)
+ https://jojoldu.tistory.com/50
+ https://365kim.tistory.com/102
