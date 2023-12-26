# :pushpin: Improved-SpringBoot-Online-Shopping-Store
> [이전 프로젝트](https://github.com/Kim-Gyuri/SpringBoot-Online-Shopping-Store)를 개선한 버전의 프로젝트다.

git branches는 다음과 같이 사용하였습니다. <br>
- [main](https://github.com/Kim-Gyuri/bookstore) : 프로젝트 정리노트
- [master](https://github.com/Kim-Gyuri/bookstore/tree/master) : 프로젝트 V1 버전 ( sales 테이블 없고 개선하기 전 코드)
- [improve](https://github.com/Kim-Gyuri/bookstore/tree/improve) : 현재 개선 중인 버전 (sales 테이블 추가, V1 문제점 개선)

## Features
+ MVC  - 로그인 처리(based 쿠키, 세션, 인터셉터), 파일 업로드(상품 이미지 등록)
+ Querydsl - 페이징처리( 모든상품 정렬/ 카테고리 정렬/ 상품명 검색)
+ CRUD
  + 회원등록, 회원수정, 회원조회
  + 상품등록
  + 상품조회 ( 회원ID로 조회, 카테고리별 조회,  상품 검색)
  + 로그인, 로그아웃 
  + 상품구매, 장바구니
  
## Technology Stacks
` Backend`
+ Java 11
+ Spring Boot 2.7.1
+ Spring Data JPA
+ Lombok
+ Hibernate
+ Spring Web
+ validation
+ H2 Database 
+ Querydsl
+ Gradle 
<br>

`Frontend`
+ Bootstrap template
+ Thymeleaf
<br>

## Database Schema
![erd 제출용](https://github.com/Kim-Gyuri/bookstore/assets/57389368/0627e85d-5a00-4fca-bc88-f6afa91e2625)

## Server Architecture
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/821c9e9a-17ff-4a74-b4a1-32d8e675b3b1)

<br>

## gif
`로그인` <br>
> 로그인을 하면, 메인 페이지로 이동된다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/80b0f3bc-9a64-407c-a839-067ffd362c45

<br> <br> <br>

`상품 검색` <br>
> 상품명을 입력하여 상품을 조회할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/dd90c731-e47d-40aa-87ca-8554fabec35b

<br> <br> <br>

`상품 가격필터 조건` <br>
> 가격(낮은 가격순/높은 가격순)으로 필터조건을 통해 상품을 조회할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/94ecfa28-7572-4e60-bc06-bfe99cdabd29

<br> <br> <br>

`drop menu` 
> 메뉴(개인정보/판매자 관리 페이지/상품 등록하기/ 장바구니/로그아웃)를 볼 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/9bb93254-44af-4268-88f6-999bd865647c

<br> <br> <br>

`상품 등록` <br>
> drop menu에서 (상품 등록하기)를 클릭하여, 상품 정보를 입력하면 등록할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/f35c16b5-f9e7-4b24-9389-1273563d01db

<br> <br> <br>

`상품 수정` <br>
> drop menu에서 (판매 관리)를 클릭하여 판매 관리 페이지로 들어가면, 해당 상품 정보(상품명/가격/수량/이미지)를 수정할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/4a7ccea6-0987-43c5-a1c8-d4ca631c5a19

<br> <br> <br>

`카테고리 페이지로 이동하기`  <br>
> 상단에 있는 상품 카테고리(책/음반/문구류)를 클릭하면, 카테고리별로 상품을 조회할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/4a112988-b471-416c-aabb-1fc03a0af363

<br> <br> <br>

`판매자 관리자 페이지` <br>
> 판매자 관리자 페이지를 들어가면 (월별 판매액 조회, 주문조회, 판매상품 조회)을 확인할 수 있다. <br>

https://github.com/Kim-Gyuri/bookstore/assets/57389368/0d8478cd-eaf0-466b-924a-607bdf560ee6

<br> <br> <br>

### 주문처리
> 주뭄을 요청/취소 했을 때 어떻게 구현했는지 확인하는 동작영상입니다.

`구매` <br>

https://github.com/Kim-Gyuri/bookstore/assets/57389368/abfb369a-b4f7-4b23-ae4c-554bb0facebd

<br> <br> <br>

`판매자가 주문을 확인할 때` <br>
> 피터팬 책 구매요청을 받았을 때, 판매자 관리 페이지에서 확인할 수 있다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/1d62007a-63da-48bb-81c5-1ff736714496

<br> <br> <br>

`구매자가 해당 주문을 취소했을 때` <br>
> 장바구니에서 주문을 취소하고 싶은 상품을 삭제한다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/d75ba47e-b52f-43ff-b76a-f3443185e897

<br> <br> <br>

`판매자가 주문취소를 확인할 때` <br>
> cancel 필터로 검색하여, 주문취소를 확인한다.

https://github.com/Kim-Gyuri/bookstore/assets/57389368/56642636-96a1-4889-8337-a5bb0e4021bd

<br> <br> <br>

## 프로젝트에 대한 요구사항과 핵심기능
프로젝트를 만드는 과정을 노트로 정리했다. <br> 프로젝트에 대한 요구사항 및 구조, 서비스의 핵심 기능 및 기능을 구현하기 위해 어떻게 했는지 적었다. <br> 
티스토리에도 [프로젝트/개인 프로젝트 V2편](https://thumper.tistory.com/category/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/%EA%B0%9C%EC%9D%B8%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20V2) 정리해두었다. 이전에 만든 프로젝트 [V1 편](https://thumper.tistory.com/category/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/%EA%B0%9C%EC%9D%B8%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20V1)에서 어떤 사항들을 개선했고 추가했는지 정리했다. <br>
+ [요구사항 구현&계층형 구조](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD%20%EA%B5%AC%ED%98%84%26%EA%B3%84%EC%B8%B5%ED%98%95%20%EA%B5%AC%EC%A1%B0.md)
+ [도메인 모델과 테이블 설계](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/%EB%8F%84%EB%A9%94%EC%9D%B8%20%EB%AA%A8%EB%8D%B8%EA%B3%BC%20%ED%85%8C%EC%9D%B4%EB%B8%94%20%EC%84%A4%EA%B3%84.md)
+ [Enum 활용](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/Enum%20%ED%99%9C%EC%9A%A9.md)
+ [Paging Query](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/Paging%20Query.md)
+ [파일 업로드](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/%ED%8C%8C%EC%9D%BC%20%EC%97%85%EB%A1%9C%EB%93%9C.md)
+ [오류처리와 예외처리](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/%EC%98%A4%EB%A5%98%EC%B2%98%EB%A6%AC%EC%99%80%20%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC.md)
+ [개선사항과 만났던 오류들](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/blob/main/%EB%85%B8%ED%8A%B8/%EA%B0%9C%EC%84%A0%EC%82%AC%ED%95%AD%EA%B3%BC%20%EB%A7%8C%EB%82%AC%EB%8D%98%20%EC%98%A4%EB%A5%98%EB%93%A4.md)
+ [AWS 서버 환경](https://github.com/Kim-Gyuri/bookstore/tree/main/%EB%85%B8%ED%8A%B8/AWS%20)
