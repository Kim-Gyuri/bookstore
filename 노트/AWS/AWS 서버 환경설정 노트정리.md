이번에 만든 프로젝트를 localhost:8080에서만 끝내지 않고, <br> 실제 URL 주소를 가지고 CI/CD 환경을 갖춘 서비스를 만들어 보려고 한다. <br>
스프링부트 프로젝트를 AWS 인프라에 배포해보자. <br> <br>
전체적인 시스템 구조는 다음과 같다. <br>
![springawsnote3](https://github.com/Kim-Gyuri/Improved-SpringBoot-Online-Shopping-Store/assets/57389368/854b53ac-2d9b-4249-8524-bb0b5356ad1b) <br>
하나의 EC2 혹은 리눅스 서버에 NginX 1대와 스프링 부트 Jar 2대를 사용하는 것이다. <br>
+ `1` 사용자는 서비스 주소로 접속한다. (80 혹은 443 포트)
+ `2` 엔진엑스가 필요한 배포에 따라 nginx reload 명령어를 통해 연결한다.

우선 아래 과정이 필요하다. <br>
+ `3` EC2 서버에 프로젝트를 배포한다. (EC2 생성, RDS 연동)
+ `4` Travis CI와 S3를 연동한다. (jar파일을 전달하기 위해 s3 연동이 먼저)
+ `5` 실제 배포는 AWS CodeDeploy 서비스를 이용한다.
+ `6` Travis CI 웹 서비스 설정 (깃허브 저장소 활성화)

### AWS
AWS라는 클라우드 서비스를 이용해 본격적으로 서버 배포를 해보자. <br> 
>  `클라우드 서비스` : <br> 쉽게 말하면 인터넷(클라우드)을 통해 서버, 스토리지(파일 저장소), 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것이다. <br>
AWS EC2는 서버 장비를 대여하는 것이지만, 실제로는 그 안의 로그관리, 모니터링, 하드웨어 교체, 네트워크 관리 등을 지원한다.

AWS의 EC2, S3를 사용하는데 프리티어로 무중단 배포가 불가능하다. <br> 
> 돈을 내고 2대를 사용하면 가능하다. 

<br>

### EC2
EC2, AWS에서 "리눅스 서버를 사용한다."라고 하면 EC2를 말하는 것이다.

### RDS
AWS에서 지원하는 클라우드 기반 관계형 데이터베이스다.
> 하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같이 잦은 운영 작업을 자동화하여 개발자가 개발에 집주알 수 있게 지원하는 서비스

## AWS EC2 인스턴스 생성하기
> [참고자료](https://heytech.tistory.com/390)

<br><br>
EC2 인스턴스 생성과정은 다음과 같다. <br>
+ 인스턴스 유형 선택하기 :프리티어(t2.micro) 선택
+ 세부 정보 구성
> VPC, 서브넷 등 AWS 서비스들의 네트워크 환경을 설정한다. <br> `여기서는 혼자서 1대의 서버만 사용하니 별다른 설정 없이 넘긴다`

+ 스토리지 구성 :크기(30 GiB)
> 스토리지는 `하드디스크`라고 부르는 서버의 디스크(SSD도 포함)를 이야기하며 서버의 용량을 얼마나 정할지 선택하는 단계다. <br>
> 30GB까지 프리티어로 가능하다. <br>

+ 태그 추가 : 웹 콘솔에서 표기될 태그인 Name 태그를 등록한다. (EC2의 이름을 붙인다고 생각하자)
+ 보안 그룹 : SSH 접속을 내 IP로 선택한다.
  > 보안 그룹은 `방화벽`이다. `서버로 80포트 외에는 허용하지 않는다`는 역할을 하는 방화벽이 AWS에서는 보안 그룹으로 사용된다. <br>
  > 유형 항목에서 SSH이면서 포트 항목에서 22인 경우는 AWS EC2에 터미널로 접속할 때를 이야기한다. <br> 보안은 언제나 높을수록 좋으니 pem 키 관리와 `지정된 IP에서만 ssh 접속`이 가능하도록 구성하자.
  
+ 키 페서 생성
+ EIP 할당 <br>
  > 같은 인스턴스를 중지하고 `다시 시작할 때도 새 IP가 할당된다.` <br> 즉 요금을 아끼기 위해 잠깐 인스턴스를 중지하고 다시 시작하면 IP가 변경되는 것이다. <br> 이렇게 되면 매번 접속해야 하는 IP가 변경되어서 PC에서 접근할 떄마다 IP주소를 확인해야 한다. <br>
굉장히 번거로우므로 인스턴스의 IP가 `매번 변경되지 않고 고정IP를 가지게 해야 한다` <br> 고정 IP를 할당해야 한다. <br> 탄력적 IP는 `생성하고 EC2서버에 연결하지 않으면 비용이 발생한다`
  
## EC2 서버에 접속하기
Window경우 AWS와 같은 외부 서버로 SSH 접속하려면 아래과 같다. <br>
+ pem키를 가지고, puttygen.exe를 실행하여 ppk파일로 변환한다.
+ putty.exe를 실행하여 SSH 접속한다.
> 최신 버전에서는 Auth에서 private key, Certificate에 모두 키를 불러와야 한다. <br> [EC2 - ppk등록 참고자료](https://skyversion.tistory.com/18)
  
## 아마존 리눅스 1 서버 생성 시 꼭 해야 할 설정들
자바 기반 웹 애플리케이션 (톰캣과 스프링부트)가 작동해야 하는 서버들에선 필수로 해야 하는 설정들입니다. <br>
+ 자바 11 설치 : 현재 이 프로젝트의 버전은 Java11 이다, 자바 8을 EC2에 설치한다. 
```
sudo apt search java # 명령어로 설치할 java 검색
sudo apt-get install openjdk-11-jdk # 자바 11 설치
```
> [AWS-EC2ubuntu에-java8-설치하기 참고자료](https://velog.io/@co323co/AWS-EC2ubuntu%EC%97%90-java8-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0)


+ 타임존 변경 : 한국 시간대로 변경하기
```
sudo rm /etc/localtime  # 미국시간대를 지우고
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime # KST로 변경
date # 타임존 변경된 것을 확인
```

+ 호스트네임 변경 
```
sudo hostnamectl set-hostname "변경할 이름"
sudo reboot # 서버 재부팅하고, 다시 접속하여 변경된 이름을 확인한다.
```
> [참고자료](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/set-hostname.html)

## AWS RDS 인스턴스 생성하기
[AWS RDS 환경 구성하기 + 인텔리제이 플러그인 설치](https://dawitblog.tistory.com/44)를 한다. <br>
+ 엔진 옵션 : MariaDB
>  MariaDB는 오픈소스 데이터베이스로 단순 쿼리 처리 성능이 좋다.

+ 파라미터 설정 : 파라미터 그룹 설정

## EC2에서 RDS에서 접근 확인하기
EC2에 ssh 접속을 진행한다. <br>
```
sudo apt-get install mysql-server # mysql을 설치
mysql -u 계정 -p -h Host주소       # 계정, 비밀번호, 호스트 주소를 사용해 RDS에 접속한다. (Host주소: RDS 앤드포인트를 복사한다)

# 패스워드를 입력하라는 메시지가 나오면 패스워드를 입력한다.
```

## EC2에 프로젝트 clone 받기
먼저 깃허브에서 코드를 받아올 수 있게 EC2에 git을 설치하고 clone 받는다. <br>
```
sudo apt-get install git           # git 설치, 깃허브에서 코드를 받아오기 위해
git --version                      # 설치 확인을 위해 버전확인
mkdir ~/app && mkdir ~/app/step1   # git clone으로 프로젝트를 저장할 디렉토리 생성
cd ~/app/step1                     # 생성된 디렉토리로 이동
git clone 복사한 주소               # 복사한 깃허브 주소 프로젝트를 클론 진행
cd 프로젝트명                       # 클론된 프로젝트로 이동
ll                                 # 프로젝트 확인 (gradle 폴더를 push했는지? 확인해야 한다.)
chmod +x gradlew                   # gradlew 접근 거절을 해결하기 위해 
./gradlew test                     # 테스트
```

> 여기서 프로젝트에 "gradle" 폴더들을 push 했어야 한다. <br>
> 테스트가 실패해서 수정하고 깃허브에 푸시를 했다면 프로젝트 폴더 안에서 다음 명령어를 사용하면 된다. :`git pull` <br>
> "-bash: ./gradlew: permission denied" 실행 권한이 없다면 다음 명령어 실행 : `chmod +x ./gradlew` <br>
> EC2 gradle build 시 무한 로딩 오류: (메모리문제 해결하기) [해결 참고](https://ksh-coding.tistory.com/40) <br>

<br>

> 배운것
```
현재 EC2엔 Gradle을 설치하지 않았다.
하지만 Gradle Task(test)를 수행할 수 있다.

이는 프로젝트 내부에 포함된 gradlew파일 때문이다.
gradle이 설치되지 않은 환경 혹은 버전이 다른 상황에서도 해당 프로젝트에 한해서 gradle을 쓸 수 있도록 지원하는 Wrapper파일이다.
해당 파일을 직접 이용하기 때문에 별도로 설치할 필요가 없다.
```

## 배포 스크립트
작성한 코드를 실제 서버에 반영하는 것을 배포라고 한다. <br>
```
배포라 하면 다음의 과정을 모두 포괄하는 의미라고 보면,
1) git clone 혹은 git pull을 통해 새 버전의 프로젝트 받기
2) Gradle이나 Maven을 통해 프로젝트 테스트와 빌드
3) EC2서버에서 해당 프로젝트 실행 및 재실행
```

<br>

쉘 스크립트로 작성해 스크립트만 실행하면 앞의 과정이 차례로 진행되도록 해보자. <br> 앞선 과정을 `배포할 때마다 개발자가 하나하나 명령어를 실행`하는 것은 불편함이 많았다. <br>
vim은 리눅스 환경과 같이 GUI(윈도우와 같이 마우스를 사용할 수 있는 환경)이 아닌 환경에서 사용할 수 있는 편집 도구다. <br>
```
vim ~/app/step1/deploy.sh  # 파일 생성
./deploy.sh                # 스크립트 실행
```

<br>

스크립트를 실행했을 때 오류가 발생했었다.
```
# 스크립트 실행 시 오류
./gradlew: No such file or directory 오류가 나고,


# 다시  vim nohup.out 했을 때 오류
"Unable to access jarfile" 
"Error: Unable to access jarfile /home/ec2-user/app/step1/"
/home/ubuntu/로 설정했는데 위 사용자(ec2-user)로 오류발생했다.
```

## 중간점검
스크립트 배포 과정에서 만난 오류를 해결하지 못했다. (일단 책이나 자료를 더 찾아보고, 그 다음에 2차 시도할 예정) <br>
AWS 무료 1년이 지나서 과금 우려가 있다. <br> EC2, RDS 등등 삭제해야 한다. [요금 아끼기 - 참고자료](https://may9noy.tistory.com/57)

### < 특별히 책에서 배운 것들>
#### 스프링 부트 1.5와 스프링 부트 2.0에서 시큐리티 설정의 차이점
> 스프링 부트 1.5에서의 OAuth2 연동 방법이 2.0에서는 크게 변경되었다.

스프링 부트 2방식인 Spring Security Oauth2 Client 라이브러리를 사용해서 진행한다. <br> 이유는 다음과 같다.
> 스프링 팀에서 기존 1.5에서 사용되던 spring-security-oauth 프로젝트는 <br>
유지 상태로 결정했으며 더는 신규 기능은 추가하지 않고 버그 수정 정도의 기능만 추가될 예정이며 <br>
신규 기능은 새 oauth2 라이브러리에서만 지원하겠다고 선언했다. <br>

> 스프링 부트용 라이브러리 (starter) 출시

> 기존에 사용되던 방식은 확장 포인트가 적절하게 오픈되어 있지 않아  <br>
 직접 상속하거나 오버라이딩 해야 하고 신규 라이브러리의 경우 확장 포인트를 고려해서 서계된 상태다.

추가로, 스프링 부트 2 방식의 자료를 찾고 싶은 경우 <br> 인터넷 자료들 사이에서 다음 2가지만 확인하면 된다. <br><br>
먼저 spring-security-oauth2-autoconfigure 라이브러리를 썼는지 확인하고 <br>
application.properties 혹은 application.yml 정보가 다음과 같은 차이가 있는지 비교해야 한다. <br>
```
# spring Boot 1.5 : url 주소를 모두 명시해야 한다.
---
google:
 ...
 Uri:https://....
---


# Spring Boot 2.x : client 인증 정보만 입력하면 된다.
---
spring:
  ...
  client:
  ...
---
```

<br>
 
#### 세션 저장소로 톰캣/데이터베이스/메모리 DB가 있으며 이 중 데이터 베이스를 사용하는 이유 
우리가 만든 서비스는 `애플리케이션을 재실행하면 로그인이 풀린다.` <br> 세션이 `내장 톰캣의 메모리에 저장`되기 때문이다. <br>
기본적으로 세션은 실행되는 WAS의 메모리에서 저장되고 호출된다. <br> 메모리에 저장되다 보니 `내장 톰캣처럼 애플리케이션 실행 시 실행되는 구종선 항상 초기화`가 된다. <br>
즉, `배포 할 때마다 톰캣이 재시작`된다. <br> 이 외에도 한 가지 문제가 더 있다. 2대 이상의 서버에서 서비스하고 있다면 톰캣마다 세션 동기화 설정을 해야만 한다. <br><br>
그래서 실제 현업에서는 세션 저장소에 대해 다음 3가지 중 한 가지를 선택한다고 한다. <br>
```
1) 톰켓 세션을 사용한다.
일반적으로 별다른 설정을 하지 않을 때 기본적으로 선택되는 방식이다.
이렇게 될 경우, 톰캣(WAS)에 세션이 저장되기 때문에 2대 이상의 WAS가 구동되는 환경에서는 톰켓들 간의 세션 공유를 위한 추가 설정이 필요하다.

2) MySQL과 같은 데이터베이스를 세션 저장소로 사용한다.
여러 WAS 간의 공용 세션을 사용할 수 있는 가장 쉬운 방법이다.
많은 설정이 필요 없지만, 결국 로그인 요청마다 DB IO가 발생하여 성능상 이슈가 발생할 수 있다.
보통 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도에서 사용한다.

3) Redis, Memcached와 같은 메모리 DB를 세션 저장소로 사용한다.
B2C서비스에서 가장 많이 사용하는 방식이다.
실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요하다.
(Redis와 같은 메모리 DB를 사용하면, 별도로 사용료를 지불해야 한다.
사용자가 없는 현재 단계에서는 데이터베이스로 모든 기능을 처리하는게 부담이 적다.)
```

<br>

세션 저장소로 데이터베이스를 사용하는 이유는, <br> `설정이 간단`하고 사용자가 많은 서비스가 아니며 비용 절감을 위해서다. <br>
+  spring-session-jdbc 등록 : 먼저 `build.gradle`에 다음과 같이 의존성을 등록한다.
+  `application.properties`에 세션 저장소 jdbc 설정추가
+  h2 확인해보기 <br>
> h2 콘솔을 확인하면 세션을 위한 테이블 2개가 생성된 것을 볼 수 있다. <br>
`JPA로 인해 세션테이블이 자동 생성`되었기 때문에 별도로 해야 할 일은 없다.

<br>

지금은 기존과 동일하게 `스프링을 재시작하면 세션이 풀린다`  <br> 이유는 H2 기반으로 스프링이 재실행될 때 H2도 재시작되기 때문이다. <br>
이후  AWS로 배포하게 되면 AWS의 데이터베이스 서비스인 RDS를 사용하게 되니 이때부터는 세션이 풀리지 않는다. <br><br><br>

#### 인텔리 [Tasks -> verification -> test] 전체 테스트 수행
test를 실행하면 롬복을 이용한 테스트 외에 스프링을 이용한 테스트는 모두 실패하는 것을 확인할 수 있다. <br><br>
설정값이 필요하다. <br> 이는 src/main 환경과 src/test 환경의 차이 때문이다. <br> 둘은 본인만의 환경 구성을 가진다. <br>
다만, src/main/resources/application.properties가 테스트 코드를 수행할 때도 적용되는 이유는 <br>
test에 application.properties가 없으면 main의 설정을 그대로 가져오기 때문이다. <br> (자동으로 가져오는 옵션 범위는 application.properties 파일까지다. <br>
즉, application-oauth.properties는 test에 파일이 엇다고 가져오는 파일이 아니다.) <br><br>
해결을 위해 테스트 환경을 위한 application.properties를 만든다.
