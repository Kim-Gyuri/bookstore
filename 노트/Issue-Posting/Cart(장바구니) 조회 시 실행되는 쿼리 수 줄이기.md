### Cart(장바구니) 조회 시 실행되는 쿼리 수가 많다.
처음 장바구니 조회를 생각했을 때 연관관계를 거쳐 가져오면 될 것 같았다. <br>
하지만 로그를 확인해보니 너무 많은 쿼리가 실행되는 것을 볼 수 있었다. <br>
```
Hibernate: 
    select
        user0_.user_id as user_id1_5_,
        user0_.city as city2_5_,
        user0_.street as street3_5_,
        user0_.zipcode as zipcode4_5_,
        user0_.cart_id as cart_id9_5_,
        user0_.email as email5_5_,
        user0_.login_id as login_id6_5_,
        user0_.name as name7_5_,
        user0_.password as password8_5_,
        user0_.sales_id as sales_i10_5_ 
    from
        users user0_ 
    where
        user0_.login_id=?
Hibernate: 
    select
        cart0_.cart_id as cart_id1_0_0_ 
    from
        cart cart0_ 
    where
        cart0_.cart_id=?
Hibernate: 
    select
        orderiteml0_.cart_id as cart_id4_3_0_,
        orderiteml0_.order_item_id as order_it1_3_0_,
        orderiteml0_.order_item_id as order_it1_3_1_,
        orderiteml0_.cart_id as cart_id4_3_1_,
        orderiteml0_.count as count2_3_1_,
        orderiteml0_.item_id as item_id5_3_1_,
        orderiteml0_.order_price as order_pr3_3_1_ 
    from
        order_item orderiteml0_ 
    where
        orderiteml0_.cart_id=?
Hibernate: 
    select
        item0_.item_id as item_id1_1_0_,
        item0_.category_type as category2_1_0_,
        item0_.item_name as item_nam3_1_0_,
        item0_.item_type as item_typ4_1_0_,
        item0_.price as price5_1_0_,
        item0_.sales_id as sales_id9_1_0_,
        item0_.seller_id as seller_i6_1_0_,
        item0_.status as status7_1_0_,
        item0_.stock_quantity as stock_qu8_1_0_ 
    from
        item item0_ 
    where
        item0_.item_id=?
Hibernate: 
    select
        imglist0_.item_id as item_id6_2_0_,
        imglist0_.item_img_id as item_img1_2_0_,
        imglist0_.item_img_id as item_img1_2_1_,
        imglist0_.img_name as img_name2_2_1_,
        imglist0_.is_main_img as is_main_3_2_1_,
        imglist0_.item_id as item_id6_2_1_,
        imglist0_.origin_img_name as origin_i4_2_1_,
        imglist0_.save_path as save_pat5_2_1_ 
    from
        item_img imglist0_ 
    where
        imglist0_.item_id=?

Hibernate: 
    select
        item0_.item_id as item_id1_1_0_,
        item0_.category_type as category2_1_0_,
        item0_.item_name as item_nam3_1_0_,
        item0_.item_type as item_typ4_1_0_,
        item0_.price as price5_1_0_,
        item0_.sales_id as sales_id9_1_0_,
        item0_.seller_id as seller_i6_1_0_,
        item0_.status as status7_1_0_,
        item0_.stock_quantity as stock_qu8_1_0_ 
    from
        item item0_ 
    where
        item0_.item_id=?
Hibernate: 
    select
        imglist0_.item_id as item_id6_2_0_,
        imglist0_.item_img_id as item_img1_2_0_,
        imglist0_.item_img_id as item_img1_2_1_,
        imglist0_.img_name as img_name2_2_1_,
        imglist0_.is_main_img as is_main_3_2_1_,
        imglist0_.item_id as item_id6_2_1_,
        imglist0_.origin_img_name as origin_i4_2_1_,
        imglist0_.save_path as save_pat5_2_1_ 
    from
        item_img imglist0_ 
    where
        imglist0_.item_id=?
```

<br>

장바구니에 담은 상품 정보를 가져오기 위해 다음과 같이 거쳐가는 쿼리가 많았다. <br>
```
장바구니의 회원 정보 조회 -> 장바구니 조회 -> 장바구니에 등록된 상품 조회 -> 상품 정보 조회
```

### QueryDsl로 조회하기
회원의 Cart id로 Cart(장바구니)에 담긴 상품을 조회하도록 작성하였다. <br>
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/dad85c5d-7cac-4f8e-8ac4-137a233b5695)

이제 Cart(장바구니)를 조회하면 아래와 같이 쿼리 한번으로 조회할 수 있게 되었다. <br>
```
Hibernate: 
    select
        orderitem0_.order_item_id as col_0_0_,
        item2_.name as col_1_0_,
        itemimg1_.save_path as col_2_0_,
        orderitem0_.count as col_3_0_,
        orderitem0_.order_price as col_4_0_ 
    from
        order_item orderitem0_ 
    left outer join
        item_img itemimg1_ 
            on (
                orderitem0_.item_id=itemimg1_.item_id
            ) cross 
    join
        item item2_ 
    where
        orderitem0_.item_id=item2_.item_id 
        and orderitem0_.cart_id=?
```

