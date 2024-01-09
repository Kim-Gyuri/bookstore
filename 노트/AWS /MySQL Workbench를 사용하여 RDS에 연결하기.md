# MySQL Workbench를 사용하여 RDS에 연결하기
MySQL Workbench를 사용하여 RDS와 연결하여 DB 및 테이블을 생성하고 데이터를 입력하였습니다. <br><br>
이번 글에서는 Workbench에서 RDS 설정하는 방법은 다루지 않않았습니다. 저는 아래 블로그글을 참고했습니다.
> [Workbench 를 통해, AWS RDS에 접속환경 설정하는 방법 - 두리안의 코딩 나무 글](https://durian9s-coding-tree.tistory.com/15)

## MySQL Workbench를 통하여 DB 및 TABLE을 생성하고 데이터를 입력하기
<details>
<summary>TABLE 생성</summary>
  
```
CREATE TABLE SPRING_SESSION ( PRIMARY_ID CHAR(36) NOT NULL, SESSION_ID CHAR(36) NOT NULL, CREATION_TIME BIGINT NOT NULL, LAST_ACCESS_TIME BIGINT NOT NULL, MAX_INACTIVE_INTERVAL INT NOT NULL, EXPIRY_TIME BIGINT NOT NULL, PRINCIPAL_NAME VARCHAR(100), CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID) );

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);

CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);

CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES ( SESSION_PRIMARY_ID CHAR(36) NOT NULL, ATTRIBUTE_NAME VARCHAR(200) NOT NULL, ATTRIBUTE_BYTES VARBINARY NOT NULL, CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME), CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE );

# table item 오류!
# 오류 메시지를 보면 MariaDB에서 "by default as identity" 구문에서 문제가 발생했다고 나와 있습니다. 
# MariaDB에서는 보통 "by default as identity" 대신 "AUTO_INCREMENT"를 사용하여 자동 증가되는 열을 만듭니다.
create table item (
        price integer,
        stock_quantity integer,
        item_id BIGINT AUTO_INCREMENT,
        sales_id bigint,
        category_type varchar(255) check (category_type in ('BOOK','MUSIC','STATIONERY')),
        item_type varchar(255) check (item_type in ('HIGHEST','BEST','LOWER')),
        name varchar(255),
        seller_id varchar(255),
        status varchar(255) check (status in ('SELL','SOLD_OUT')),
        primary key (item_id)
);

CREATE TABLE CART (
    CART_ID BIGINT NOT NULL,
    PRIMARY KEY (CART_ID)
);

CREATE TABLE ITEM (
    PRICE INTEGER,
    STOCK_QUANTITY INTEGER,
    ITEM_ID BIGINT AUTO_INCREMENT,
    SALES_ID BIGINT,
    CATEGORY_TYPE VARCHAR(255) CHECK (CATEGORY_TYPE IN ('BOOK', 'MUSIC', 'STATIONERY')),
    ITEM_TYPE VARCHAR(255) CHECK (ITEM_TYPE IN ('HIGHEST', 'BEST', 'LOWER')),
    NAME VARCHAR(255),
    SELLER_ID VARCHAR(255),
    STATUS VARCHAR(255) CHECK (STATUS IN ('SELL', 'SOLD_OUT')),
    PRIMARY KEY (ITEM_ID)
);

CREATE TABLE ITEM_IMG (
    IS_MAIN_IMG TINYINT CHECK (IS_MAIN_IMG BETWEEN 0 AND 1),
    ITEM_ID BIGINT,
    ITEM_IMG_ID BIGINT NOT NULL,
    IMG_NAME VARCHAR(255),
    ORIGIN_IMG_NAME VARCHAR(255),
    SAVE_PATH VARCHAR(255),
    PRIMARY KEY (ITEM_IMG_ID)
);

CREATE TABLE ORDER_ITEM (
    COUNT INTEGER,
    ORDER_DATE DATE,
    ORDER_PRICE INTEGER,
    CART_ID BIGINT,
    CREATED_DATE TIMESTAMP(6),
    ITEM_ID BIGINT,
    MODIFIED_DATE TIMESTAMP(6),
    ORDER_ITEM_ID BIGINT NOT NULL,
    SALES_ID BIGINT,
    ORDER_STATUS VARCHAR(255) CHECK (ORDER_STATUS IN ('ORDER', 'CANCEL')),
    PRIMARY KEY (ORDER_ITEM_ID)
);

CREATE TABLE SALES (
    TOTAL_REVENUE INTEGER NOT NULL,
    SALES_ID BIGINT NOT NULL,
    PRIMARY KEY (SALES_ID)
);

CREATE TABLE USERS (
    CART_ID BIGINT UNIQUE,
    SALES_ID BIGINT UNIQUE,
    USER_ID BIGINT NOT NULL,
    CITY VARCHAR(255),
    EMAIL VARCHAR(255),
    LOGIN_ID VARCHAR(255),
    NAME VARCHAR(255),
    PASSWORD VARCHAR(255),
    STREET VARCHAR(255),
    ZIPCODE VARCHAR(255),
    PRIMARY KEY (USER_ID)
);
```
</details>

<details>
<summary>데이터 값 삽입</summary>

```
use aladin; 

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('1984.jpg',0,1,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/c87b0c45-1ac6-41c3-beb2-82cd80d8c7fd',1);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','And Then There Were None',8000,NULL,'test3','SELL',10,2);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('And Then There Were None.jpg',0,2,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/62c68662-fd5a-4e15-90a3-7279d99c94cf',2);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','Anne of Green Gables',7900,NULL,'test3','SELL',10,3);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Anne of Green Gables.jpg',0,3,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/2e564f8b-4367-4acf-99bf-77e56958991c',3);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','Damian',7700,NULL,'test3','SELL',10,4);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Damian.jpg',0,4,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/d679e445-39d9-43cd-8b83-9af46df52d1f',4);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','Little Women',10000,NULL,'test3','SELL',10,5);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Little Women.jpg',0,5,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/082cbfb9-deaa-479e-8fb3-87b561509012',5);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','MOMO',10000,NULL,'test3','SELL',10,6);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('MOMO.jpg',0,6,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/e0bd1c01-d9aa-41a7-9ae2-f56f761195cd',6);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','My Sweet Orange Tree',5000,NULL,'test3','SELL',10,7);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('My Sweet Orange Tree.jpg',0,7,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5f7fac5-f1e5-4f34-92a2-c5f52c820403',7);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','HIGHEST','Peter Pan',4900,NULL,'test3','SELL',10,8);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Peter Pan.jpg',0,8,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/19e5bc6b-3eda-4dab-ac6e-c799ebbdbb6c',8);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','BEST','Seven Years of Darkness',12000,NULL,'test3','SELL',10,9);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Seven Years of Darkness.jpg',0,9,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/d39ca436-a351-4e91-92bf-2f5b55eb047b',9);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','BEST','Tara Duncan',1000,NULL,'test3','SELL',10,10);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Tara Duncan.jpg',0,10,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/145237d6-b511-4dcd-947a-492589fdbee8',10);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','LOWER','The Blue Bird for Children',10000,NULL,'test3','SELL',10,11);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('The Blue Bird for Children.jpg',0,11,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/effeec6a-1e61-4e99-87db-b28fba714c08',11);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('BOOK','LOWER','The Diary of Anne Frank',20000,NULL,'test3','SELL',10,12);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('The Diary of Anne Frank.jpg',0,12,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/f704fd45-7e33-48d9-a5f3-cc729132b85b',12);


insert into CART (cart_id) values (1);

insert into SALES (total_revenue,sales_id) values (0,1);


USE aladin;

# ChatGPT
# 이전에 언급한 대로, "Incorrect string value" 오류가 발생하는 경우 문자열 인코딩의 문제일 가능성이 높습니다. 
# 여러 가지 방법으로 이 문제를 해결할 수 있습니다.
# 테이블을 생성할 때 또는 이미 생성된 테이블에서 열의 문자 집합을 UTF-8로 설정합니다.
ALTER TABLE users MODIFY COLUMN city VARCHAR(255) CHARACTER SET utf8;

insert into users (city,street,zipcode,cart_id,email,login_id,name,password,sales_id,user_id) values ('진주','2','2222',1,'karis99@naver.com','test3','userC','test3!',1,1);

ALTER TABLE USERS MODIFY COLUMN city VARCHAR(255) CHARACTER SET utf8;

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','Edith Piaf',14200,NULL,'test4','SELL',10,13);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Edith Piaf.jpg',0,13,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/08498786-065d-472f-96f4-bab7b019141e',13);


insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','Warner Classics',13300,NULL,'test4','SELL',10,14);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Warner Classics.jpg',0,14,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/f5ddb6fa-cf02-4208-b615-ff3eefcbf5ca',14);


insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','Utopia',10300,NULL,'test4','SELL',10,15);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Utopia.jpg',0,15,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/2aa0fec5-2e03-4eb2-aeb3-635874fa56c0',15);


insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','Utada Hikaru',10100,NULL,'test4','SELL',10,16);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Utada Hikaru.jpg',0,16,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/3a0df0b4-ca73-49c7-9b59-10270cb1ad9d',16);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','totoro',10190,NULL,'test4','SELL',10,17);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('totoro.jpg',0,17,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/62a76cee-a563-4934-be0e-06b72903ee44',17);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','sm',8810,NULL,'test4','SELL',10,18);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('sm.jpg',0,18,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/d141de99-308c-4585-b02d-6e27fd8c5351',18);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','HIGHEST','Sia',7710,NULL,'test4','SELL',10,19);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Sia.jpg',0,19,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/edd76fca-4377-4de1-bb91-db87c5ecd845',19);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','BEST','pororo',10090,NULL,'test4','SELL',10,20);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('pororo.jpg',0,20,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/410b31ce-21a0-4041-88d0-3d391d90ac70',20);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','BEST','NewJeans',8930,NULL,'test4','SELL',10,21);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('NewJeans.jpg',0,21,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/11cc1176-7a3b-4206-b698-5e137f93ef49',21);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','BEST','Miriam Makeba',7710,NULL,'test4','SELL',10,22);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Miriam Makeba.jpg',0,22,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/2c6bad42-7a57-456e-8cab-8c42e32b10f8',22);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','LOWER','La La Land OST by Justin Hurwitz',10000,NULL,'test4','SELL',10,23);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('La La Land OST by Justin Hurwitz.jpg',0,23,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/27d1f3a8-0a07-472c-be4f-9ff59a33a9a7',23);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','LOWER','John Coltrane',10150,NULL,'test4','SELL',10,24);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('John Coltrane.jpg',0,24,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/9981c6e7-f3f2-428b-bf16-bd289ce8fd0a',24);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','LOWER','BLACKPINK',16100,NULL,'test4','SELL',10,25);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('BLACKPINK.jpg',0,25,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/e6d6777f-7e4f-49d0-9d30-ed59202610c9',25);

insert into CART (cart_id) values (2);

insert into SALES (total_revenue,sales_id) values (0,2);

insert into USERS (city,street,zipcode,cart_id,email,login_id,name,password,sales_id,user_id) values ('진주','2','2222',2,'bambi05@naver.com','test4','userD','test4!',2,2);

insert into CART (cart_id) values (3);

insert into SALES (total_revenue,sales_id) values (0,3);

insert into USERS (city,street,zipcode,cart_id,email,login_id,name,password,sales_id,user_id) values ('진주','2','2222',3,'mimi05@naver.com','test','userA','test!',3,3);

insert into CART (cart_id) values (4);

insert into SALES (total_revenue,sales_id) values (0,4);

insert into USERS (city,street,zipcode,cart_id,email,login_id,name,password,sales_id,user_id) values ('진주','2','2222',4,'nana10@naver.com','test2','userB','test2!',4,4);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','BEST','Ariana Grande',12000,NULL,'test2','SELL',10,26);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('Ariana Grande.jpg',0,26,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/3bd66ffe-53fe-4e7f-b676-4909ccddbb4b',26);

insert into ITEM (category_type,item_type,name,price,sales_id,seller_id,status,stock_quantity,item_id) values ('MUSIC','BEST','BAEK HYUN',30000,NULL,'test2','SELL',10,27);

insert into ITEM_IMG (img_name,is_main_img,item_id,origin_img_name,save_path,item_img_id) values ('BAEK HYUN.jpg',0,27,'origin.jpg','https://github.com/Kim-Gyuri/bookstore/assets/57389368/27392311-6b6c-4852-8087-6a12834b9908',27);

insert into ORDER_ITEM (cart_id,count,created_date,item_id,modified_date,order_date,order_price,order_status,sales_id,order_item_id) values (3,5,NULL,26,NULL,'2023-09-10 00:00:00',60000,'ORDER',NULL,1);

insert into ORDER_ITEM (cart_id,count,created_date,item_id,modified_date,order_date,order_price,order_status,sales_id,order_item_id) values (3,2,NULL,27,NULL,'2023-07-22 00:00:00',60000,'ORDER',NULL,2);

update SALES set total_revenue=120000 where sales_id=4;

update ITEM set category_type='MUSIC',item_type='BEST',name='Ariana Grande',price=12000,sales_id=NULL,seller_id='test2',status='SELL',stock_quantity=5 where item_id=26;

update ITEM set category_type='MUSIC',item_type='BEST',name='BAEK HYUN',price=30000,sales_id=NULL,seller_id='test2',status='SELL',stock_quantity=8 where item_id=27;
```
</details>

위의 SQL을 작성하면, 아래와 같이 데이터가 입력되었음을 확인할 수 있다. <br>
![workbench](https://github.com/Kim-Gyuri/bookstore/assets/57389368/b8b931da-8054-4b1d-816b-d1d6dbc434eb)

## RDS에서 확인하기
DB로 aladin를 만들었으며, <br> 
![rds 생성된 데이터베이스](https://github.com/Kim-Gyuri/bookstore/assets/57389368/cdb81fa5-5bea-44f1-86ae-2d9f4c048ce4)

aladin에 생성한 테이블을 확인할 수 있다. <br>
![rds 확인 - 생성된 테이블 확인](https://github.com/Kim-Gyuri/bookstore/assets/57389368/2e7ec7e5-ba7d-4a8d-ba4c-c564f38706f2)

### ITEM 테이블 조회
![select item](https://github.com/Kim-Gyuri/bookstore/assets/57389368/f27c43ba-c3ba-46ed-a16f-7ff9e493ad25)
### USER 테이블 조회
![select users](https://github.com/Kim-Gyuri/bookstore/assets/57389368/a44ceea4-2df5-4313-ae86-b1f019cfdab5)
### ORDERITEM 테이블 조회
![select order-item](https://github.com/Kim-Gyuri/bookstore/assets/57389368/8335a735-8a4a-4090-aa71-f5530bf59836)
