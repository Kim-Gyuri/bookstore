# 📌 EC2 서버에 프로젝트 배포해보기
스프링 부트 프로젝트를 AWS EC2 서비스에 배포하기 위한 과정은 다음과 같다.
+ EC2에 Git 설치 및 프로젝트 clone
+ 배포 스크립트 만들기
+ 스프링 부트 프로젝트로 RDS 접근하기
  + 테이블 생성 (`mysql workbench로 rds 연결`)
  + 프로젝트 설정 (MariaDB에서 사용 가능한 드라이버를 프로젝트에 추가한다.)
  + EC2 설정 (EC2 서버 내부에 DB 접속 정보를 설정한다.) 
  +  퍼블릭 IPv4 DNS 뒤에 8080을 붙여 접근한다.


## 1. EC2에 Git 설치 및 프로젝트 clone

```
sudo yum install git     # EC2로 접속한다
git --version            #  설치가 완료되면 설치 상태를 확인한다.


# 깃이 성공적으로 설치되면 git clone으로 프로젝트를 저장할 디렉토리를 생성한다.
mkdir ~/app && mkdir ~/app/step1  

# 생성된 디렉토리로 이동한다
cd ~/app/step1                                         



# 복사한  https 주소를 통해 git clone을 진행한다.
git clone "복사한 주소"	            

# git clone이 끝났으면 클론된 프로젝트로 이동해서 파일들이 잘 복사되었는지 확인한다.
cd "프로젝트명"  
ll
```

다음과 같이 프로젝트의 코드들이 모두 있으면 된다. <br>
![cd 프로젝트](https://github.com/Kim-Gyuri/bookstore/assets/57389368/fcd2be75-e5bb-4d16-b3d9-4aa25121e831)

다음 명령어로 실행 권한을 추가한 뒤 테스트를 수행한다.
```
chmod +x ./gradlew
./gradlew test
```

> "gradle > Tasks > verification > test"를 통과했다면, 정상적으로 테스트를 통과한다.



## 2. 배포 스크립트 만들기
작성한 코드를 실제 서버에 반영하는 것을 배포라고 한다. <br> 다음의 과정을 모두 합쳤다.
+ git clone 혹은 git pull을 통해 새 버전의 프로젝트 받는다.
+ Gradle / Maven을 통해 프로젝트 Test & Build 실행한다.
+ EC2 서버에서 해당 프로젝트 실행 및 재실행한다.

<br>

deploy.sh 파일을 생성한다. (리눅스 환경에서의 편집; vim 도구)
```
vim ~/app/step1/deploy.sh
```

![배포 스크립트 만들기](https://github.com/Kim-Gyuri/bookstore/assets/57389368/c6e57a86-d5ea-4212-b098-b3d859dd8abf)
> nohup은 실행시킨 jar 파일의 로그 내용을 `nohup.out` 파일에 남긴다.

생성한 스크립트에 실행 권한을 추가한다.
```
chmod +x ./deploy.sh
```

권한이 추가되었는지 확인한다.
```
cd ~/app/step1
ll
```
![deploy x권한](https://github.com/Kim-Gyuri/bookstore/assets/57389368/92e57625-3218-4c1c-b0ba-2526b3b85f58)


## 3. 스프링 부트 프로젝트로 RDS 접근하기
MariaDB에서 스프링부트 프로젝트를 실행하기 위해서는 몇 가지 작업이 필요하다. <br>
+ 테이블 생성  (직접 쿼리를 이용하여 생성해야 한다.)
+ 프로젝트 설정 (MariaDB에서 사용 가능한 드라이버를 프로젝트에 추가한다.)
+ EC2 설정 (EC2 서버 내부에 DB 접속 정보를 설정한다.) 
+  퍼블릭 IPv4 DNS 뒤에 8080을 붙여 접근한다.

### RDS 테이블 생성
인텔리제이 커뮤니티 버전으로 RDS 연결하는 과정에서 연결오류가 발생했었다. <br> 그래서 mysql workbench로 테이블을 생성한 후, ec2에 접속하여 rds 접근하여 DB를 확인하기로 했다. <br><br>
rds 보안그룹 편집, mysql workbench로 RDS 연결과정은 다음과 같다.
+ 로컬에서 RDS에 접근하기 위해서는 RDS의 보안그룹에 로컬 IP를 추가해 주어야 한다. <br> Mysql/Aurora 타입을 선택하고, 소스 탭에서 내 IP를 선택한다.
+ Mysql 워크벤치에서 새 연결 설정 <br> 테스트 코드 수행 시 로그로 생성되는 쿼리를 사용하여 테이블을 생성하면 된다.
![workench 설정](https://github.com/Kim-Gyuri/bookstore/assets/57389368/823f8ffa-55c1-4578-b032-96768db4fe26)
> mysql workbench에서 connection 생성할 때, Test connection(접속 테스트)를 성공한다면 <br>
> password를 입력하여 접속하면 된다.

다음과 같이 쿼리를 실행한다.
```
create table cart (cart_id bigint not null, primary key (cart_id))

create table item (item_id bigint not null, category_type varchar(255), item_name varchar(255), item_type varchar(255), price integer, status varchar(255), stock_quantity integer, user_user_id bigint, primary key (item_id))

create table item_img (item_img_id bigint not null, img_name varchar(255), is_main_img integer, origin_img_name varchar(255), save_path varcha(255), item_id bigint, primary key (item_img_id))

create table order_item (order_item_id bigint not null, count integer, order_price integer, cart_id bigint, item_id bigint, primary key (order_item_id))

create table users (user_id bigint not null, city varchar(255), street varchar(255), zipcode varchar(255), email varchar(255), login_id varchar(255), name varchar(255), password varchar(255), cart_id bigint, primary key (user_id))
```


+ EC2에서 RDS에서 접근 확인
putty를 사용하여 ssh 접속 후 다음 명령어를 실행한다.
```
sudo yum install mysql              # mysql 설치
mysql -u "계정" -h "host주소"       # RDS에 접속


show databases;     # 생성된 DB 확인
use shopping_store  # 만든 DB 선택
show tables # 만든 테이블 확인
```

![rds 확인](https://github.com/Kim-Gyuri/bookstore/assets/57389368/67f83bf9-d517-462f-b289-0aeb692046fe)

> `참고자료`<br> [AWS RDS 환경설정 - mysql Workench 사용](https://gaga-kim.tistory.com/entry/Spring-Boot-07%EC%9E%A5-AWS%EC%97%90-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%ED%99%98%EA%B2%BD%EC%9D%84-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EC%9E%90-AWS-RDS)


### 프로젝트 설정
MarriaDB 드라이버를 build.gradle에 등록한다.
```
implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
```

### EC2 설정
RDS 접속 정보도 보호해야 할 정보이니 EC2 서버에 직접 설정 파일을 추가한다.
```
vim ~/app/application-real-db.properties
```

![ec2 설정 - rds 접속정보 입력](https://github.com/Kim-Gyuri/bookstore/assets/57389368/71ed6378-e44c-4329-a6fc-87d28b094d56)
> `spring.jpa.hibernate.ddl-auto=none` <br>
> JPA로 테이블이 자동 생성되는 옵션은 none으로 지정한다. (자동생성 되지 않도록 설정) <br>
> (RDS에는 실제 운영으로 사용될 테이블이니 절대 스프링 부트에서 새로 만들지 않도록 해야 한다.)



###  퍼블릭 IPv4 DNS 뒤에 8080을 붙여 접근한다.
이렇게 설정한 후 deploy.sh를 실행하고 nohup.out 파일을 확인한다.  <br>
nohup.out 파일에 "Tomcat started on port(s) : 8080 (http) ..." 로그가 보인다면 성공적으로 수행된 것이다. <br>
그 다음 퍼블릭 DNS로 실제 브라우저에서 확인하면 된다. <br>
```
./deploy.sh
vim nohup.out
java -jar bookstore-0.0.1-SNAPSHOT.jar 
```

`./deploy.sh`를 다시 실행하고 <br>
![deploy sh 실행했을 때](https://github.com/Kim-Gyuri/bookstore/assets/57389368/aef6bc06-bb70-4bac-93e8-abea82ff5b3b) <br><br>
`vim nohup.out`를 열어본다. <br>
![vim nohup 했을 때](https://github.com/Kim-Gyuri/bookstore/assets/57389368/808082f2-1334-438b-97b9-e0b80f9d09a2) <br><br>
`java -jar 명령어`를 통해 웹사이트를 띄운다. <br>
![실행](https://github.com/Kim-Gyuri/bookstore/assets/57389368/80997a4d-efe8-4c12-8783-06cf7e48b87b) <br> <br>

실제 브라우저에서 확인했을 때, 아래와 같이 성공하였다.
![dns 접속화면](https://github.com/Kim-Gyuri/bookstore/assets/57389368/5f118fbe-8e82-441c-9812-1bf027683d82)

## 그 다음
여기까지 스프링부트 프로젝트를 EC2에 배포하는 과정을 정리했다. 스크립트를 작성해 간편하게 빌드와 배포를 진행할 수 있었다. <br> 
하지만 지금 방식은 '수동 Test' '수동 Build' 과정이 필요하다.
+ 항상 작업을 진행할 때마다 수동으로 전체 테스트를 수행해야 한다.
+ (만약 다른 사람이 작성한 브랜치와 합쳤을 때) 이상이 없는지 build를 수행하여 체크해야 한다.

수동 Test/Build를 자동화 시키는 작업은  TravisCI & AWS CodeDeploy로 할 수 있다고 한다.
