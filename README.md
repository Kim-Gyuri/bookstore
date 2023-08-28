# :pushpin: Improved-SpringBoot-Online-Shopping-Store
> [이전 프로젝트](https://github.com/Kim-Gyuri/SpringBoot-Online-Shopping-Store)를 개선한 버전의 프로젝트다.

프로젝트 코드는 master 브랜치로 넣어놨는데, [링크](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/tree/master)로 이동하면 읽어볼 수 있다. <br>


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
![ERD](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/5eeeda0f-39f3-4ee7-bd2b-0a119a8d8e76) 

## Server Architecture
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/821c9e9a-17ff-4a74-b4a1-32d8e675b3b1)

<br>

## gif
`로그인` <br> <br>
![aladin - 중고 거래 사이트 외 페이지 1개 - 프로필 1 - Microsoft_ Edge 2023-07-18 17-43-21 (online-video-cutter com)](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/280ca6f0-71e3-4f06-8171-5a4fc7ce0dbf)

<br> <br> <br>

`카테고리 페이지로 이동하기`  <br> <br>
![카테고리](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/328f53e5-89ac-45df-bf81-333b0e97795f)

<br> <br> <br>

`drop menu` 
> 메뉴(개인정보/등록한 상품/상품 등록하기/ 장바구니/로그아웃)를 볼 수 있다.

<br>

![로그아웃_메뉴_AdobeExpress_AdobeExpress](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/cddf4667-a138-4920-8fd2-86a85932711c)

<br> <br> <br>

`구매` <br><br>
![장바구니 (online-video-cutter com)](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/f01fc0db-8b47-4962-ab71-8e991b207770)

<br> <br> <br>

`상품 등록` <br><br>
![상품등록 (online-video-cutter com)](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/ac51dcd4-21f1-4029-b508-ba715047ae63)

<br> <br> <br>

`가격순 필터` <br>

![가격필터_AdobeExpress](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/105dbd44-cc7e-4322-bc98-819bd29c78ad)

<br> <br> <br>

`검색` <br>
> 메인/카테고리 페이지에서 검색하기

<br>

![검색_AdobeExpress](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/7a789c47-024c-4a0c-bf17-aad8044186d7)

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
