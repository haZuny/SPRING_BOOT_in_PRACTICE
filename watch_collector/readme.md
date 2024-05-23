# 프로젝트 소개

### #프로젝트 조건: 
- Web 페이지를 구현하는 프로젝트
- 2개 이상의 Domain을 사용하는 프로젝트
- CRUD 모두 구현하는 프로젝트
- DB 사용하기
- 1인 개발 프로젝트이므로 템플릿은 최소화 시키기

### #프로젝트 이름: Watch Collector

### #프로젝트 주제: 개인이 보유하고 있는 시계들의 관리를 도와주는 웹 사이트
- ***주제 선정 동기***
  - 최근에 스마트워치를 팔고 패션 시계를 구입하면서 시계에 관심을 가지게됨
  - 다양한 시계의 디자인에 그만 눈이 돌아가버림,,
  - 당근, 번개장터를 통해 저렴하고 예뻐보이는 시계 무지성 구매,,,
  - 가지고 있는 시계의 갯수가 많아지다 보니 시계의 스펙(모델번호, 사이즈, 구동 방식)등을 까먹게됨
  - 내가 가지고 있는 시계들의 스펙을 정리해주는 사이트를 만들면 어떨까라는 생각으로 프로젝트 시작

- ***기능***
  - 로그인 기능 구현
    - JWT 인증 등의 복잡한 방식 사용 X
    - ID, PW를 단순 String으로 받아 DB에 저장, 추후 로그인시 사용자가 입력한 값과 매칭
    - 회원 탈퇴 기능 구현
  
  - 시계 CRUD 기능 구현
    - 입력받는 필드:
      - Model Name: 모델 번호
      - Movement: 구동 방식 및 무브먼트
      - Case Size: 케이스 크기
      - Lug To Lug: 러그 투 러그 사이즈
      - Glass: 전면 유리 소재
      - Function: 추가 기능(여러가지 가능, 문페이즈, 에코드라이브, 전파 송신 등등)
      
      
---

# 개발 환경
- Spring boot 3.2.5(Gradle-Groovy)
- Java 21
- MySQL

---

# Wire Frame
![](https://velog.velcdn.com/images/hj3175791/post/56cb282d-3be6-4c08-a4e3-63ab0af21862/image.jpg)

추후 자세히 그릴 예정..ㅎ

### Templates:
- ***./home.html***
- ***./watch_list.html***
- ./watch
  - ***./watch/createForm.html***
  - ***./watch/updateForm.html***


---

# ER-Diagram
![](https://velog.velcdn.com/images/hj3175791/post/72bc18c1-d485-4df3-be46-32d2ae01e6f4/image.jpg)
+ watch_func(watch_id, function)

추후 자세히 그릴 예정

---

# API

### # home
- ***홈페이지***
  - `~/`
  - `GET`
    - Req: X
    - Res: `./home.html`
    
### # user
- ***가입***
  - `~/user/new/`
  - `GET`
    - Req: X
    - Res: `./user/createForm.html`
  - `POST`
    - Req: 
      - id: String
      - pw: String
    - Res: `Redirect`
- ***탈퇴***
  - `~/user/<userId>/delete/`
  - `POST`
    - Req:
      - id: String
      - pw: String
    - Res: `./home.html`
- ***시계 목록***
  - `~/user/<userId>/watches/`
  - `GET`
    - Req: X
    - Res: `/watch/list.html`
 
### # watch

- ***시계 추가***
  - `~/<userId>/watch/new/`
  - `GET`
    - Req: X
    - Res: `./watch/createForm.html`
  - `POST'
    - Req: 
      - id: String
      - pw: String
    - Res: `Redirect`
- ***시계 정보 변경***
  - `~/<userId>/watch/<watchId>/update/`
  - `GET`
    - Req: X
    - Res: `./watch/updateForm.html`
  - `POST`
    - Req: 
      - id: String
      - pw: String
    - Res: `Redirect`
- ***시계 삭제***
  - `~/<userId>/watch/<watchId>/delete/1`
  - `POST`
    - Req:
      - id: String
      - pw: String
    - Res: `Redirect`
    
---

# 프로젝트 결과
![](https://velog.velcdn.com/images/hj3175791/post/45a8cb62-7ba3-427a-ad8a-a277dbf67181/image.png)

![](https://velog.velcdn.com/images/hj3175791/post/e013b71d-a834-456d-b953-56ebacdf9191/image.png)

![](https://velog.velcdn.com/images/hj3175791/post/4a5a187b-c677-493d-9312-e2d673d3de21/image.png)

- User 및 Watch CRUD 가능 
- Watch의 데이터 조작시, id 및 pw 인증

---

# 느낀 점
- 스프링 Web Layer를 처음 고려해서 만들어 봤는데, 코드를 작성하면서 각 계층간의 역할이 모호하게 된것 같아서 아쉽다.
- 기능별 단위 테스트를 처음 작성해봤다. 테스트 코드의 존재 덕에 확실히 코드 수정이 편해진것같다.
- 클래스별 의존 관계 주입의 중요성을 한층 체감할 수 있었다. 다음에 프로젝트를 진행할때는 클래스별 의존관계도 고려해서 설계를 해야 할 필요성을 느꼈다.
- 설계의 부실함을 체감했다.