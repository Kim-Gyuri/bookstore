이전 프로젝트를 만들었을 때, Querydsl 사용하는 부분 코드가 아쉬었었다. <br> 그래서 이번에 페이징 쿼리를 수정하면서 다시 복습하고자 한다. <br> <br>
#### 고치고 싶었던 것
```
 1. 컨트롤러 부분에서 카테고리별로 페이징처리하는 부분
 2. 페이징 필터 수정
```

<br> 

카테고리별로 상품 페이징하는 부분에서 아쉬운점이 있다.
![카테고리 컨트롤러 문제코드](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/822acf94-d952-4c59-a7d5-f93ee9e1eda4) <br><br>
이전 프로젝트에서 카테고리 구현을 하고자, 컨트롤러에서 아래 경로 매핑 2개를 작성했었다. <br>
```
/category/BOOK
/category/MUSIC
```
{code} 방식으로 받는 방법을 몰라서, 반복되는 코드를 적고 code를 문자열로 고정시켜 페이지 구현을 했었다. <br> 하지만, queryDSL 쿼리를 작성하여 좀 더 코드개선을 할 수 있었다. <br><br>

또한 페이징 필터를 "낮은가격순" "높은가격순"으로 고쳤다.  <br> 이전에 "이름순" "최신등록순" 필터를 만들었는데 가격순으로 수정하려고 한다. <br>
![화면캡처](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/a7d393b6-c453-4739-b5a2-49b7794d625a) <br><br> <br><br><br>

이번 포스팅에서는 아래 사항에 대해 정리하고자 한다. <br>
+ Querydsl을 Spring Boot Data Jpa에서 Querydsl을 어떻게 설정하는지?
+ Querydsl 작성
+ 리팩토링

그럼 이제 시작해보겠습니다.

## Querydsl
querydsl을 통하여 생성되는 정적 Q-type 클래스를 이용하여 SQL과 같은 쿼리를 생성하도록 도와주는 프레임워크이다. <br>
JPA뿐만 아니라 MongoDB, JDO, Lucene과 같은 라이브러리도 제공하고 있다. <br> 앞에서 말했던 JPQL의 단점을 완벽하게 커버할 수 있는 Querydsl은 타입에 안전한 방식으로 쿼리를 실행할 수 있다. <br>
타입을 통하여 쿼리를 작성하므로 도메인 모델의 프로퍼티 변경에 유연하게 대처 가능하다. <br> <br><br>

### Spring Data Jpa Custom Repository 적용
Spring Data Jpa에서는 Custom Repository를 JpaRepository 상속 클래스에서 사용할 수 있도록 기능을 지원한다. <br> 전체적인 그림은 아래와 같다. <br> <br>
![사용자 정의 리포지토리 구성](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/31a2b01a-b122-4293-b600-53b6e5f2bdef) <br><br>
위와 같이 구성하면 ItemRepository에서 ItemRepositoryCustomImpl의 코드도 사용할 수 있다.
> 일종의 공식으로, custom이 붙은 인터페이스를 상속한 Impl 클래스의 코드는 Custom 인터페이스를 상속한 JpaRepository에서도 사용할 수 있다. <br>
> Custom과 Impl만 외워도 된다.

먼저 ItemRepository와 같은 위치에 ItemRepositoryCustom(인터페이스), ItemRepositoryCustomImpl(구현체) 클래스를 생성한다. <br>
그리고 ItemRepositoryCustom(인터페이스), ItemRepositoryCustomImpl(구현체)에 다음과 같은 코드를 추가한다. <br>
+ custom 코드
+ Impl 코드

<br>

코드는 아래와 같다. <br><br>
`ItemRepository` 코드 <br>
![리포지](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/ddd8f61b-de1d-479f-9dd5-7653c9b8af0b) <br><br>
`ItemRepositoryCustom` 코드 <br>
![custom](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/04012b31-e522-4219-be3a-d950e14ca078)<br><br>
`ItemRepositoryCustomImpl` 코드 <br>
![Impl](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/1898a141-ec41-4db1-b2fb-ce2fa61aa55a)<br><br>

<br><br><br>

### Querydsl 작성
#### 1. 의존성 추가
![설정코드1](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/8a54bc74-a5ad-4062-b636-f1a161e5f389)
![설정코드2](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/addead42-1c7b-4b90-96d7-9f76ec0ad3cd)

 <br>
 
#### 2. Q-type클래스 
+ Gradle IntelliJ 사용법
> Gradle ➔ Tasks ➔ build ➔ clean <br> Gradle ➔ Tasks ➔ other ➔ compileQuerydsl
+ Q 타입 생성 확인
> build  ➔ generated ➔ querydsl

 <br>
 
#### 3. join 쿼리
.join() 사용 가능하다.

 <br>
 
#### 4. 페이징 쿼리
페이징 쿼리 또한 JPQL과 매우 유사하다. <br> Querydsl의 이전 버전에서는 fetchResults(), fetchCount() 메소드를 이용하여 페이징 쿼리를 작성했었다. <br>
그러나 5.0.0 버전 이후 deprecated 되었다. <br>
> count 쿼리가 모든 dialect에서 또는 다중 그룹 쿼리에서 완벽하게 지원되지 않았기 때문에 deprecated되었다.  <br>
> 이제 fetchCount() 대신 fetch.size()로 동일한 결과를 얻을 수 있다.  <br>

 <br>
 
#### 5. Dto로 Projection 하기
> 자주 사용되는 특정 쿼리의 결과는 Dto로 따로 분리하여 가져올 때가 많다. <br> 프로젝션과 결과 반환하기 위해 @QueryProjection을 사용한다.

+ 조회용 DTO를 추가한다.
+ Q-type Dto 생성한다.

 <br>

#### 6.  동적쿼리 성능 최적화 조회 
where절 파라미터를 사용한다.

 <br>

#### 7. 스프링 데이터 페이징 활용
+ Querydsl 페이징 연동 :스프링 데이터의 Page, Pageable을 활용한다.
+ CountQuery 최적화 :PageableExecutionUtils.getPage()로 최적화한다.

 <br>

#### 8. 정렬
![페이징 코드 예2](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/71e76416-bcbb-430f-85fc-bb4d077b2c8d) 
+ desc() : 내림차순
+ asc() : 올림차순
> sortByItemPriceASC() 로직에, asc()를 사용하여 낮은가격순 페이징처리를 했다.

<br>

#### 9. BooleanExpression을 활용한 리팩토링
![BooleanExpression을 활용한 리팩토링](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/0d95597c-c64d-4e61-b582-866612b0f4d2) <br> <br>
BooleanExpression을 사용하면 QueryDSL Repository의 표현을 좀더 직관적으로 볼 수 있도록 리팩토링할 수 있다. <br>
+ itemNameContains(), categoryTypeContains() 메서드를 생성하여 BooleanExpression 타입을 리턴하도록 한다.
+ BooleanExpression을 리턴하는데, 각 메소드에서 조건이 맞지 않으면 null을 리턴한다.
+ null을 리턴하니 where에서는 상황에 따라 조건문을 생성하게 된다.

<br><br>

예시로, 아래 코드처럼 작성하였다. <br>
> 전체코드는 링크를 참고해주세요. [ItemRepositoryCustomImpl 전체코드](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/master/src/main/java/springstudy/bookstore/repository/ItemRepositoryCustomImpl.java)

![페이징 코드 예1](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/20942a1c-e637-423c-ad52-33cec77fea38) <br><br><br>

이 코드가 정상적으로 작동하는지 테스트를 해보자.
위와 같이 기본 repository로 조회하여 확인하면 된다.
> [코드링크 첨부](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/master/src/test/java/springstudy/bookstore/repository/ItemRepositoryTest.java)


### 후기
이제 명확하게 쿼리가 예측 가능하게 되었다. <br> 다음에는 추가로 [페이징 성능개선 참고글](https://jojoldu.tistory.com/528)를 읽고 프로젝트 리팩토링을 했으면 좋겠다.


