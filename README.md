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


### :🫀rt:HomeFragment🫀rt:
![image](https://raw.githubusercontent.com/seongssu/ImageUrl/main/home.gif) 

##### CODE
- ViewModel&LiveData를 이용해서 데이터를 관리합니다.
- SharedPreferences를 이용해서 카테고리 id를 저장합니다.
- powerSpinner 라이브러리를 이용해서 스피너를 사용하고 카테고리를 선택할 수 있습니다.
- addOnScrollListener, infinity Scroll을 이용해서 스크롤을 감지하고 추가데이터를 요청하고 응답받습니다.
- registerForActivityResult를 이용해서 아이템을 클릭했을때 VideoDetail페이지로 이동되고, 데이터를 넘겨줍니다.

##### 설명
- 1줄 데이터 : 동영상 API를 이용해서 "Most Popular Videos" 목록을 리사이클러뷰로 화면에 표시해줍니다.
- 카테고리 목록 : VideoCategories API를 이용해서 카테고리 목록과 id를 받아와서 스피너에 표시해주고 id는 spf를 이용해 저장합니다.
- 저장했던 id를 이용해서 앱을 재실행했을때 마지막 선택했던 카테고리를 확인할 수 있고 검색됩니다.
- 2줄 데이터 : 카테고리에서 받은 id와 동영상API를 이용해서 목록을 리사이클러뷰로 화면에 표시해줍니다.
- 3줄 데이터 : 2줄데이터에서 받아온 Channerid와 채널API를 이용해서 목록을 리사이클러뷰로 화면에 표시해줍니다.
- 카테고리를 선택하면 2줄데이터, 3줄데이터 모두 갱신됩니다.
- 데이터 요청 중 로딩중일때 observe를 이용해서 관찰하고 LiveData를 이용해서 프로그레스바를 표시해줍니다.
- 항목의 갯수에 따라서 검색결과 우측 상단에 숫자로 표시되고, 추가 데이터에의해 갱신됩니다.
- 내용은 최대 2줄까지 표시되고, 초과되는 내용은 ...으로 대체됩니다.
- 우측 상단에 있는 프로필을 누르면 MyPage로 이동됩니다.

### 🫀SearchFragment🫀:
![image](https://raw.githubusercontent.com/seongssu/ImageUrl/main/search.gif)

##### CODE
- searchView를 이용해서 검색 합니다.
- powerSpinner 라이브러리를 이용해서 스피너를 사용하고 카테고리를 선택할 수 있습니다.
- ViewModel&LiveData를 이용해서 데이터를 관리합니다.
- addOnScrollListener, infinity Scroll을 이용해서 스크롤을 감지하고 추가데이터를 요청하고 응답받습니다.
- registerForActivityResult를 이용해서 아이템을 클릭했을때 VideoDetail페이지로 이동되고, 데이터를 넘겨줍니다.

##### 설명
- 카테고리를 선택하지 않고 검색어를 입력하면 "카테고리를 먼저 선택해주세요!!" 라는 토스트 메시지를 표시합니다.
- 스피너를 이용해서 카테고리를 선택하고 카테고리id와 검색 API를 이용해서 검색결과 데이터를 요청하고 응답받은 데이터를 리사이클러뷰의 GridView를 이용해서 화면에 표시해줍니다.
- 카테고리에따라서 데이터가 갱신됩니다.
- 데이터 요청 중 로딩중일때 observe를 이용해서 관찰하고 LiveData를 이용해서 프로그레스바를 표시해줍니다.
- 항목의 갯수에 따라서 검색결과 우측 상단에 숫자로 표시되고, 추가 데이터에의해 갱신됩니다.
- 내용은 최대 2줄까지 표시되고, 초과되는 내용은 ...으로 대체됩니다.
- 우측 상단에 있는 프로필을 누르면 MyPage로 이동됩니다.

### 🫀rt:VideoDetail🫀rt:
![image]()

##### CODE
- ViewModel&LiveData를 이용해서 데이터를 관리합니다.
- AlertDialog.Builder를 이용해서 세부정보를 표시합니다.
- sharedPreferences를 이용해서 데이터를 저장합니다.

##### 설명
- 홈, 검색, 마이 프래그먼트에서 비디오 아이템을 클릭하면 해당 Position의 아이템을 Json 데이터로 변환 후 intent에 첨부하여 디테일 액티비티로 넘어옵니다.
- intent에 첨부된 데이터를 YoutubeModel 객체로 변환하여 이용합니다.
- YoutubeModel 객체를 이용하여 Webview 위젯을 사용해 Youtube 동영상을 실행합니다.
- 상단의 플로팅 버튼을 통해 정보, 공유, 저장을 할 수 있습니다.
    - 정보 버튼을 누르면  channel, CommentTrhreads 리소스를 받아오는 API 통신을 통해 응답 객체를 받아옵니다.
        - 응답 객체의 데이터를 활용하여 영상과 그 영상의 채널에 대한 통계, 정보등을 표현합니다.
        - 댓글의 경우 인피니티 스크롤이 적용된 리사이클러뷰로 표현이 됩니다.
        - 댓글, 태그, 내용의 경우 이미지를 클릭함으로써 내용을 가리거나 나타나게 할 수 있습니다.
    - 공유 버튼을 누르면 공유 인텐트 객체를 통해 링크를 공유하는 액티비티를 띄웁니다.
    - 저장 버튼을 누르면 SharedPreferences를 통해 YoutubeModel 객체를 저장합니다.
- LiveData와 Observe 메서드를 이용해 MVVM패턴을 적용하려는 시도를 하였습니다.
    - VideoDetailActivitity
        - VideoDetailViewModel, VideoRepository 인스턴스를 호출하였습니다.
        - 다른 프래그먼트에서 받는 비디오 객체 데이터를 제외하고는, View에 대한 속성 변경 코드로 대부분의 코드가 이루어져 있습니다.
        - 데이터의 변경, 가공과 관련된 부분은 ViewModel에서 적용하였습니다.
        - ViewModel의 LiveData들을 호출하여 Observe 메서드를 통해 LiveData가 변경되면 일어날 실행문들을 작성하였습니다.
        - 초기 설정, 플로팅 버튼, 다이어로그, 공유인텐트, 웹뷰를 실행하는 함수들이 있습니다.
    - VideoDetailViewModel
        - YoutubeModel, VideoRepository을 입력값으로 받습니다.(YoutubeModel은 Video 리소스를 통해 응답받은 객체중 필요한 데이터만 담은 Model입니다.)
        - 다른 프래그먼트에서 받은 응답객체와, channel, CommentTrhreads 리소스를 받아오는 API 통신을 통해 반환된 응답 객체에 대한 데이터들중 일부를 LiveData로 선언합니다.
        - 버튼 클릭을 감지하기 위한 변수를 LiveData로 선언합니다.
        - 여러가지 데이터들을 선언하고, 그 데이터의 변경에 대한 여러가지 함수들이 있습니다.
        - 마이 페이지 프래그먼트의 보관함에 데이터를 저장하고 삭제하는 함수가 있습니다.
        - channel, CommentTrhreads 리소스를 받아오는 함수가 있습니다.
    - VideoDetailViewModelFactory
        - VideoDetailViewModel 인스턴스 호출시 입력값을 줄 수 있게 만들기 위한 클래스 입니다.
    - VideoRepository
        - 데이터의 변경에 대한 여러가지 함수들이 있습니다.
    - VIdeoDetailCommentAdapter
        - 다이어로그창에서 댓글의 표시에 대한 리사이클러뷰의 어댑터 입니다.

### 🫀rt:MyPage🫀rt:
![image]()
##### CODE
- ViewModel&LiveData를 이용해서 데이터를 관리합니다.
- sharedPreferences를 이용해서 비디오데이터를 저장합니다.
- Room을 이용해서 프로필사진, id, nickname을 저장합니다.
- TextWatcher를 이용해서 유효성검사를 합니다.
- onActivityResult, intent를 이용해서 DOCUMENT에서 이미지를 가져옵니다.

##### 내용
- 사용자 프로필 정보와 보관함(RecyclerView)을 포함하고 있는 마이페이지 레이아웃은 보관함(RecyclerView)에 데이터 바인딩을 적용하였고,  CoordinatorLayout을 사용하여 스크롤에 따라 앱 바가 축소 및 확장되는 효과를 적용하였습니다. 
- 보관함 아이템이 없을 경우 버튼 클릭시 비디오 검색 페이지로 이동할 수 있도록 하였습니다. 
- 사용자의 프로필 정보는 버튼 클릭 시 다이얼로그 화면에서 수정할 수 있으며 유저데이터는 Room를 사용하여 입력값을 저장, 삭제 할 수 있습니다.   
- 프로필 사진은 Document에 저장된 이미지를 불러올 수 있으며 삭제가 가능합니다. 닉네임과 아이디 입력 시 실시간 유효성검사로 통해 조건에 맞지 않으면 에러메시지가 표시됩니다. 
- 사용자의 프로필 정보가 갱신되면 Home Search Fragment, DetailAcitvity 앱 바 프로필도 함께 갱신되도록 하였으며 각 페이지에서 클릭 시 마이페이지로 이동할 수 있도록 하였습니다. 
- Detail Acitivity에서 "좋아요" 버튼을 통해 추가된 비디오는 내부 SharedPreference에  저장되며, Myvideofragment에서는 이 정보를 가져와서 표시합니다.
-  Myvideofragment 보관함의 아이템이 클릭되면 클릭된 아이템(**`item`**)을 **`Gson`**을 사용하여 JSON 문자열로 직렬화하고,  Intent로Detail Acitivity에 전달합니다. 
- Room 라이브러리와 AAC ViewModel(MyVideoViewModel)을 활용하여 MVVM 패턴을 구현
  
