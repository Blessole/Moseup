

# Moseup



“**모**두의 **습**관”

목표를 함께 이루어내는 습관 스터디 게더링 플랫폼 웹사이트

## **Description**



### 목적/동기

- 코로나19 팬데믹 이후, 자기 관리 및 계발에 대한 수요가 증가하는 추세입니다. MOSEUP은 이러한 사회적 현상에서 아이디어를 착안하여, ‘함께 만드는 습관 스터디’를 기획하게 되었습니다.
- 이용자들이 더 건강한 Mental Health를 추구하고, Well-being의 목표를 이룰 수 있도록 스터디 게더링 및 습관 인증 서비스를 제공하기위해 프로젝트를 기획하였습니다.
- Spring Boot, JPA, Gradle, Thymeleaf 를 적극적으로 활용하고 Github를 통해 협업을 하고 싶었습니다.

### 기대 효과

- 자신이 관심 있는 스터디에 가입을 하거나, 혹은 본인이 스터디 장이 되어 스터디를 기획할 수 있습니다.
- 마음이 맞는 사람들과 함께 모여, 좋은 습관을 만들 수 있습니다.
- 스터디 가입 시 예치금을 지불하고, 스터디 완료 시 인증 횟수만큼 예치금이 환불되어 습관 형성 동기부여에 도움이 될 수 있습니다.

### 프로젝트 기간 / 인원

- 2022년 7월 8일 ~ 2022년 8월 29일 (Refactoring 진행 중)
- 4명 (Back-end)
    - 권기준, 김솔, 김진만, 정찬우

## Techs Used


- back-end
    - Java 11
    - Spring Boot 2.7.3
    - Gradle
    - Spring Data JPA
    - MySQL 8.0
    - Lombok
- front-end
    - Javascript
    - jQuery
    - HTML 5
    - Thymeleaf
- server
    - Apache Tomcat v9.0
- collaboration
    - Github

## Project design



### Architecture

![architecture](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3844a2af-254c-4490-ae14-8f006f7d6f6c/Untitled.png)

### Flowchart

![Untitled](Moseup%2015eaf12a729f417cbf5b2181d96530e7/Untitled%201.png)

### ERD

![Untitled](Moseup%2015eaf12a729f417cbf5b2181d96530e7/Untitled%202.png)

## **Specific description**



- 회원가입
- 관리자페이지
- 메인화면
- 팀페이지
    - 각 팀에 해당하는 게시글을 불러와서 조회 및 수정할 수 있습니다.
    - 각 게시글에 해당하는 댓글을 불러올 수 있습니다.
    - 팀에 가입한 회원을 조회 할 수 있습니다.
