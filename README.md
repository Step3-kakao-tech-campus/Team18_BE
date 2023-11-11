# Garden - BE

## 🖥️ 프로젝트 소개

한국어 교육 플랫폼 Garden은 한류 문화를 기반으로 영상들을 통해 한국어 학습의 장벽을 극복하고, 외국인들에게 멘토링 형태로 학습 환경을 제공합니다.

## 🕰️ 개발 기간

- 23.09.26 - 23.11.11

### 🧑‍🤝‍🧑 멤버구성 및 맡은 역할

#### FE

- 타임 키퍼 : 정성현 - 멘토 게시글
- 테크 리더 : 채민아 - 로그인, 회원가입, 영상 프로필
- 리마인더 : 강바다 - 채팅

#### BE

- 조장 : 이윤수 - 멘토 게시글, 영상
- 기획 리더 : 강효정 - 로그인, 회원가입, 유저, 프로필
- 리액셔너 : 진승현 - 멘토 멘티 관계, 배포
  ​

### ⚙️ 개발 환경

- `Java 11`
- **IDE** : IntelliJ
- **Framework** : Springboot(2.7.6)
- **Database** : Maria DB
- **ORM** : JPA

### 🎈 인스턴스 주소

[https://k6306e208fe94a.user-app.krampoline.com](https://ke94a9914479ca.user-app.krampoline.com/)

### 🎨 ERD

​<img src="https://github.com/Step3-kakao-tech-campus/Team18_BE/assets/37840237/04fa2308-d8e8-4a8a-8d9c-172ed90925df"/>

## 📌 주요 기능

#### 회원가입

- 이메일 중복 확인

#### 로그인

- 로그인 성공 시, access token과 refresh token 발급

#### Access Token 및 Refresh Token

- 로그인 성공 시, access token과 refresh token 발급
- access token이 만료되었을 경우, refresh token을 통해 새로운 access token 재발급

#### BaseTime 적용

- 데이터베이스의 정보를 완전삭제하는 hard delete가 아닌 deleted_at을 true로 하는 soft delete 방식 적용
- 엔티티 생성 시간, 수정 시간, 삭제 시간 저장

#### 프로필 정보 조회 및 수정

- 마이 페이지에서 본인의 프로필 정보 조회
- 멘토링 페이지에서 다른 사용자가 본인의 프로필 정보 조회
- 프로필 수정 전, 비밀번호 확인을 통한 본인 확인

#### 영상 페이지 -

- 영상들을 필터링에 따라 조회 기능
- 사용자가 관심있는 영상 추천 기능
- 영상 페이지 조회 기능
- 사용자별 영상 시청기록 조회 기능

#### 멘토링 게시글 페이지 -

- 멘토 유저의 글 작성 기능
- 게시글들을 필터링에 따라 조회 및 필터링 기능
- 게시글 페이지 조회, 수정, 삭제, 만료 요청 기능

#### 멘토링 대시보드 페이지

- 멘토, 멘티 기준 신청목록 조회 기능
- 멘토 : 멘티의 신청을 수락 / 거부할 수 있는 기능
- 멘티 : 멘토링 신청 / 취소 기능
- 멘토링 대시보드 화면, 멘토링 완료 화면의 게시글 갯수를 조회할 수 있는 기능

#### 멘토링 완료 페이지

- 멘토, 멘티 기준 멘토링이 맺어진 목록 조회 기능

#### 배포

- 크램폴린을 활용하여 최종 배포 진행

<img src="https://github.com/Step3-kakao-tech-campus/Team18_BE/assets/37840237/f1f0ddee-2f46-4e51-b6d6-e9bc677509b2"/>

### 테스트 결과 보고서 / 시나리오 명세서

- BE 시나리오 명세서 : [https://www.notion.so/BE_-d9855d96b2764e18a79ccc8b0bac5a3b](https://www.notion.so/18-_BE_-d9855d96b2764e18a79ccc8b0bac5a3b)
- BE 테스트 결과 보고서 : [https://www.notion.so/18-_BE_-b63cb7010a694ae4958cbe9655b57731](https://www.notion.so/18-_BE_-b63cb7010a694ae4958cbe9655b57731)
