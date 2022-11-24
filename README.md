# EnjoyTrip Final 관통프로젝트 서울 10반 공예찬 이우진
---
#### 공공 데이터 포털의 한국관광공사 관광정보서비스 데이터를 사용한 여행 정보를 제공하는 사이트 제작 프로젝트
 

---
 

## [ 개요 ]
- developer : 공예찬, 이우진
- 개발언어 : Frontend(Vue2, HTML5, CSS3, Javascript), Backend(SpringBoot, Java, SQL, MyBatis)
- 사용한 api : Vue BootStrap, Kakaomap, 한국관광공사 관광정보서비스
- 개발도구 : VS Code, STS, MySQL
 
---
 

## [ Usecase Diagram ] 
![image](./resources/diagram/UsecaseDiagram.png)
 

---
 

## [ Class Diagram ]
![image](./resources/diagram/ClassDiagram.bmp)
 

---
 

## [ Database ER Diagram ]
![image](./resources/diagram/ERDiagram.png)
 

---
 

## [구현화면 및 기능]

### 1. 홈 화면 및 기능


<img src="./resources/capture/01-1.home_open_nav.png" width="400">
<span>
<img src="./resources/capture/01-3.home_open_nav_after_login.png" width="400">

- 홈 화면입니다. 왼쪽 사이드 메뉴는 로그인 유무에 따라 메뉴가 변경됩니다.

<img src="./resources/capture/01-2.home_close_nav.png" width="400">

- 사이드 메뉴는 버튼을 통해 접고 펼칠 수 있습니다.

---

### 2. 회원 화면 및 기능

<img src="./resources/capture/02-1.login.png" width="400">
<span>
<img src="./resources/capture/02-2.join.png" width="400">

- 로그인/회원가입 화면입니다. 회원 로그인과 회원 가입 기능, 쿠키를 활용한 아이디 저장 기능이 있습니다.

<img src="./resources/capture/02-3.find_password.png" width="400">

- 비밀번호 찾기 화면입니다. 비밀번호 찾기 시 회원 정보를 입력하고 일치하는 계정이 존재하는 경우 임시 비밀번호로 변경시켜줍니다.

<img src="./resources/capture/02-4.mypage.png" width="400">
<span>
<img src="./resources/capture/02-5.modify_user_info.png" width="400">

- 회원 정보 열람/수정 화면입니다.

<img src="./resources/capture/02-6.withdraw_user.png" width="400">

- 회원 탈퇴 화면입니다. 비밀번호 확인 절차를 진행한 후에 탈퇴를 진행합니다.

---

### 3. 여행정보 화면 및 기능

<img src="./resources/capture/03-1.map.png" width="400">
<span>
<img src="./resources/capture/03-2.attraction_detail.png" width="400">

- 여행 지도 화면과 관광지 상세 정보 화면입니다. 도/시/군/구/분류에 따라 지도에 마커가 표시되며, 해당 위치 마커를 클릭시 관광지 상세 정보 화면이 팝업으로 열립니다.

<img src="./resources/capture/03-3.travel_course_make.png" width="400">
<span>
<img src="./resources/capture/03-4.travel_course_make_input.png" width="400">

- 여행코스 만들기 화면입니다. 도/시/군/구/분류/검색어에 따라 관광지 목록이 갱신되며, 관광지 목록에서 원하는 관광지를 드래그 앤 드롭으로 움직여 여행 경로를 생성할 수 있습니다. 여행 경로 순서 변경이 가능하며 경로 순서에 따라 지도에 선이 그려집니다. 제목과 내용을 작성하여 여행코스를 공유할 수 있습니다.

<img src="./resources/capture/03-5.travel_course_list.png" width="400">
<span>
<img src="./resources/capture/03-6.travel_course_detail.png" width="400">

- 공유된 여행코스 목록 화면입니다. 여행코스 목록에서 원하는 여행코스를 선택하면 해당 여행코스의 내용과 경로를 지도에 표시해줍니다.

---

### 4. 게시판 화면 및 기능

<img src="./resources/capture/04-1.board_list.png" width="400">
<span>
<img src="./resources/capture/04-2.board_list_search.png" width="400">

- 게시판 목록 화면입니다. 페이지네이션 기능과 검색 기능, 정렬 기능이 있습니다.

<img src="./resources/capture/04-3.board_write.png" width="400">
<span>
<img src="./resources/capture/04-4.board_view.png" width="400">
<span>
<img src="./resources/capture/04-5.board_modify.png" width="400">

- 게시판 글 관련 화면입니다. 게시글 작성, 수정, 삭제가 가능하며, 게시글 안에서는 댓글 작성, 삭제가 가능합니다.
