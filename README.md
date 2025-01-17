# :pushpin: Improved-SpringBoot-Online-Shopping-Store

## 소개
Aladin | 회원끼리 물건을 팔 수 있는 중고 거래 서비스 
> 개발기간 (2023.06.02 ~2023.08.02) <br>
> 1명 (개인 프로젝트) <br>
>  [이전 프로젝트](https://github.com/Kim-Gyuri/SpringBoot-Online-Shopping-Store)를 개선한 버전의 프로젝트다.


## 브랜치
git branches는 다음과 같이 사용하였습니다. <br>
- main : 프로젝트 하면서 기록한 노트
- master : 이전 프로젝트 V1 버전을 복사한 것 (개선하기 전 코드)
- improve : 개선 버전! (sales 테이블 추가, V1 문제점을 개선한다.)
  
## 주요 기능
+ 회원관리
  + 회원가입, 로그인, 로그아웃
+ 상품관리
  + 상품 등록/수정/삭제
+ 주문
  + 구입/구매취소/주문조회

## 핵심성과
+ Restful API 구현
+ 모든 서비스에 대한 Unit 테스트 코드 작성
+ AWS 배포
  + EC2 서버 배포, RDS(MariaDB) 설정, S3를 활용한 상품 이미지 업로드
+ SALES 테이블 추가
  + 판매 상품 관리 및 주문 정보와 판매액 조회
+ 상품 검색 기능
  + 가격, 카테고리, 상품명 기준으로 검색
+ 페이지 구현
  + 메인 페이지, 상품 등록, 장바구니, 판매자 페이지
+ 주문 상품 조회 시 쿼리 수 최적화
  + N+1 문제 해결을 위해 Fetch Join을 사용한 QueryDsl로 단일 쿼리로 조회
+ 커스텀 예외 처리
  + 사용자 정의 예외를 통해 예외 처리 로직 구현


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

## User Flow
![image](https://github.com/user-attachments/assets/a3ec1cf9-cb7c-4d48-a286-7744d29ebc93)


## Database Schema
![erd 제출용](https://github.com/Kim-Gyuri/bookstore/assets/57389368/0627e85d-5a00-4fca-bc88-f6afa91e2625)

## Server Architecture
![image](https://github.com/Kim-Gyuri/bookstore/assets/57389368/821c9e9a-17ff-4a74-b4a1-32d8e675b3b1) <br>

AWS 인프라 관련 설정은 다음과 같다. <br>
+ AWS EC2 인스턴스 생성
+ AWS RDS 데이터베이스 생성
+ EC2와 RDS 간 연동
+ AWS EC2 서비스에 스프링 부트 프로젝트를 배포 (deploy 배포 스크립트)
+ 스프링 부트 프로젝트와 AWS RDS 연동
+ S3 연동하여 이미지 업로드

### EC2 
Amazon Linux 2 프리티어 선택

### RDS
MariaDB 프리티어 선택
> `로컬PC에서 RDS 접근 확인` :로컬PC에서 RDS 접근 확인하기 위해 Mysql workbench를 사용한다.

###  AWS S3
회원이 상품등록할 때 첨부하는 상품이미지를 관리하기 위해 AWS S3를 사용한다.

<br>

## 화면 캡처
### 상품 판매
![image](https://github.com/user-attachments/assets/ff6c08f1-b3c7-4190-bea2-dec1bcf104d0) <br>
![image](https://github.com/user-attachments/assets/6bbcbb4b-a9ba-47dc-9012-8b6c466a0f78) <br><br>

### 상품 구매 
![image](https://github.com/user-attachments/assets/ce377f88-5edf-47a9-a896-cd65575d6ab4) <br>
![image](https://github.com/user-attachments/assets/f3985b40-bcac-4734-835a-a54bd43d89ee)


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
