저번 프로젝트 만들 때, 아쉬웠던 점들을 최대한 보완해보려고 한다.
### 아쉬웠던 부분
+ Item 정보 중, ItemType(상품등급), ItemCategory(상품카테고리) 설계하는 더 나은 방법을 찾았으면 한다.
+ 카테고리별로 상품 페이징하는 부분에서 아쉬운점이 있다.
+ 그 외 코드 리팩토링

## Enum 활용
> 이전 프로젝트 설계했을 때, <br> Item 필드에 ItemType(상품 등급), ItemCategory(상품 카테고리)가 있는데 String 문자열로 선언했었다. <br><br>
![Item 필드설계시](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/0a955642-ee73-4c94-b524-c1b33b9b4786) <br>

### 자바의 enum 타입을 매핑한다.
+ enum을 활용하여, 객체간 책임을 확실히 분리하도록 하자.
+ 객체가 상태(값)와 행위(로직)을 갖고 있는 것이 좋다.  <br>

상품 등급과 카테고리를 명확히 표현하며, 각 타입은 본인이 수행해야할 기능과 책임만 가질 수 있게 하려면 <br> 기존 방식으로는 해결하기가 어렵다고 생각하였습니다. <br>
그래서 이를 Enum으로 전환하였습니다. <br> <br> Java의 Enum은 결국 클래스인 점을 이용하여, Enum의 상수에 카테고리 종류 문자열을 갖도록 하였습니다. <br>
각 Enum 상수들은 본인들이 갖고 있는 문자열들을 확인하여 문자열 인자값이 어느 Enum 상수에 포함되어 있는지 확인할 수 있게 되었습니다. <br> <br>
#### enum 클래스는 다음과 같다. <br> 
특정 typeCode가 있을 때 이 값이 어떤 카테고리 타입인지 CategoryType에 직접 물어보면(enumOf) 된다. <br> 추가할 카테고리 타입이 있는 경우엔 CategoryType에 추가하면 된다. <br><br>
![CategoryType enum타입](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/77726f35-a753-4bc0-b740-66ad11116dad) <br> <br><br>
ItemType도 enum 타입으로, 상품 등급정보를 명확하게 표현한다. <br><br> 
![ItemType enum타입](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/6545567e-b317-49a9-8552-798bff901a53) <br><br><br>
#### 다음은 enum 이름으로 매핑한다.
![엔티티 @Enumerated](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/ba95f9ad-2ac4-4c48-b288-e514a74e4182) <br><br>
JPA  경우, 위 처럼 @Enumerated(EnumType.STRING)를 선언했을 때 Enum 필드가 테이블에 저장시 Enum의 이름으로 저장된다. <br>
전체 값이 변경되버리는 위험한 일이기 때문에 Enum에선 @Enumerated를 함께 사용해야 한다. <br>
> 자바 ORM 표준 JPA 프로그래밍 책, 4.7.2절을 참고하면 좀 더 자세히 확인할 수 있다. 

<br><br>

#### 컨트롤러에서 사용하기
> `변경 전` <br>
> ![이전 코드 ItemController](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/19e35c97-7023-459e-a60d-c5c58bd30048) 

<br><br>

`개선했을 때` <br> select box로 그룹 리스트를 출력해야하는 경우엔 CategoryType.enumOf()을 사용하면 아주 쉽게 대응할 수 있다 <br><br>
![코드개선 ItemController](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/1a5e1d4d-83f4-4d6a-9f01-cbd84ff85412) <br>
![타임리프 설계](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/58d2382e-f191-4fa6-b263-3db4b50a32f4) <br><br><br>
뷰 템플릿 코드를 작성할 때, Enum 값을 가져와서 쓰면 된다. 상품 등록할 때 '카테고리 선택' '상풍등급 선택' select 박스를 구현해야 하는데, Enum의 값을 순회하면서 값을 출력해주었다. <br>

+  뷰에서 사용할 Enum을 모델을 통해서 뷰에 전달한다.
+  Controller에서 보낸 "categoryTypes" "itemTypes"를 반복문을 돌면서 th:text, th:value로 출력한다.

<br> 

#### 테스트 코드를 작성해서 확인해보자.
카테고리 타입코드에 따라 CategoryType에게 물어보고 값을 가져오는 것을 명확하게 확인할 수 있다. <br>
![enumOf() 테스트](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/f31775f6-b532-4078-b434-453dd5c7f9eb)

<br><br>

### 후기
Enum 활용했을 때, Item에 필요한 CategoryType, ItemType, ItemSellStatus (카테고리 타입, 상품등급, 판매상태) 등등 역할을 명확하게 보이도록 설계할 수 있게 되었다. <br>
"객체간 책임을 확실히 분리" 하도록, 필요한 경우 enum을 활용하면 좋을 것 같다고 느꼈다.

### 참고자료
> [Enum 활용 참고글](https://jojoldu.tistory.com/137)
