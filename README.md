![image](https://raw.githubusercontent.com/seongssu/ImageUrl/main/5%EC%A1%B0%20%EB%A1%9C%EA%B3%A0.png)

## :heart_on_fire:OhJIJO STREAMING:heart_on_fire:
### 유튜브 비디오를 입맛대로 볼 수 있는 앱 입니다.

## 🫀rt:MEMBER🫀rt:
|직책|이름|MBTI|취미|
|--|---|----|--------|
| 팀장 | 이동규 |INFP|롤|
| 팀원 | 김현정 |ENFP|유튜브,넷플릭스|
| 팀원 | 박성수 |ESFJ|드라마|

## 기능 구현
![image](https://raw.githubusercontent.com/seongssu/ImageUrl/main/%EA%B8%B0%EB%8A%A5%EA%B5%AC%ED%98%84.png)


### 🫀:HomeFragment🫀:
![image](https://raw.githubusercontent.com/seongssu/ImageUrl/main/home.gif) 

##### CODE
- ViewModel&LiveData를 이용해서 데이터를 관리합니다.
- SharedPreferences를 이용해서 카테고리 id를 저장합니다.
- powerSpinner 라이브러리를 이용해서 스피너를 사용하고 카테고리를 선택할 수 있습니다.
- addOnScrollListener, infinity Scroll을 이용해서 스크롤을 감지하고 추가데이터를 요청하고 응답받습니다.

##### 설명
- 1줄 데이터 : 동영상 API를 이용해서 "Most Popular Videos" 목록을 리사이클러뷰로 화면에 표시해줍니다.
- 카테고리 목록 : VideoCategories API를 이용해서 카테고리 목록과 id를 받아와서 스피너에 표시해주고 id는 spf를 이용해 저장합니다.
-   저장했던 id를 이용해서 앱을 재실행했을때 마지막 선택했던 카테고리를 확인할 수 있고 검색됩니다.
- 2줄 데이터 : 카테고리에서 받은 id와 동영상API를 이용해서 목록을 리사이클러뷰로 화면에 표시해줍니다.
- 
- 데이터 요청 중 로딩중일때 observe를 이용해서 관찰하고 LiveData를 이용해서 프로그레스바를 표시합니다.
- 

