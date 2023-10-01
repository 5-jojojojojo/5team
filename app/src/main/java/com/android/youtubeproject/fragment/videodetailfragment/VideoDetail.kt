package com.android.youtubeproject.fragment.videodetailfragment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.android.youtubeproject.R
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.ActivityVideoDetailBinding
import com.android.youtubeproject.databinding.DialogVideoDetailInformationBinding
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoDetail : AppCompatActivity() {
    private val binding by lazy { ActivityVideoDetailBinding.inflate(layoutInflater) }
    var btmstate = false
    var bt3state = false
    lateinit var data2: YoutubeModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
        binding.videoDetailFbtmain.setOnClickListener {
            ButtonAction("main")
        }
        binding.videoDetailFbt1.setOnClickListener {
            ButtonAction("infomation")
        }
        binding.videoDetailFbt2.setOnClickListener {
            ButtonAction("share")
        }
        binding.videoDetailFbt3.setOnClickListener {
            ButtonAction("like")
        }
    }

    fun initialize() {
        overridePendingTransition(R.anim.anim_video_detail, R.id.video_detail_constraint)
        val data = intent.getStringExtra("itemdata")
        val gson = Gson()
        val type = object : TypeToken<YoutubeModel>() {}.type
        data2 = gson.fromJson(data, type)
        bt3state = MyPageFunc.isExist(data2)

        val webView: WebView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.WHITE)

        val videoId = data2.videoid
        val html = """
    <html>
    <body style="margin:0;padding:0;background:white">
        <div style="display:flex;justify-content:center;align-items:center;height:100%">
            <iframe width="100%" height="100%" src="https://www.youtube.com/embed/$videoId" frameborder="0" allowfullscreen></iframe>
        </div>
    </body>
    </html>
"""
        webView.loadData(html, "text/html", "utf-8")
    }

    fun ButtonAction(buttontype: String) {
        when (buttontype) {
            "main" -> {
                /*
                delay는 코루틴 스코프 내에서만 호출될 수 있는 함수이기 때문에 코루틴 스코프에서 호출해야 한다.
                lifecycleScope.launch를 사용하여 메인 스레드에서 코루틴을 실행할수 있다.
                한 편, 메인 스레드에서 딜레이를 주는게 안전한지 나중에 물어보기
                딜레이 타임을 한번에 조절하기 위해 Long타입 변수로 주었고,
                com.google.android.material.floatingactionbutton.FloatingActionButton 위젯은 hide()와 show()라는 메서드를 사용하여
                애니메이션을 쉽게 줄 수 있다.
                */
                lifecycleScope.launch {
                    val delaytime = 50L
                    if (!btmstate) {
                        binding.videoDetailFbtmain.hide()
                        binding.videoDetailFbt1.show()
                        delay(delaytime)
                        binding.videoDetailFbt2.show()
                        delay(delaytime)
                        binding.videoDetailFbt3.run {
                            if(bt3state) setImageResource(R.drawable.ic_video_detail_liked)
                            else setImageResource(R.drawable.ic_video_detail_disliked)
                            show()
                        }
                        delay(delaytime)
                        binding.videoDetailFbtmain.show()
                    } else {
                        binding.videoDetailFbtmain.hide()
                        binding.videoDetailFbt1.hide()
                        delay(delaytime)
                        binding.videoDetailFbt2.hide()
                        delay(delaytime)
                        binding.videoDetailFbt3.hide()
                        delay(delaytime)
                        binding.videoDetailFbtmain.show()
                    }
                    btmstate = !btmstate
                }
            }

            "infomation" -> {
                val builder = AlertDialog.Builder(this)
                val binding_dialog = DialogVideoDetailInformationBinding.inflate(layoutInflater)
                builder.setView(binding_dialog.root)
                binding_dialog.videoDetailDialogTextTitle.text = "영상 제목 : " + data2.title
                binding_dialog.videoDetailDialogTextDate.text =
                    "게시 날짜 : " + data2.date.substring(0, 10).replace("-", "/")
                binding_dialog.videoDetailDialogTextChannelname.text = "채널 명 : " + data2.channelname
                binding_dialog.videoDetailDialogTextContent.text = "영상 설명 : " + data2.description
                Glide.with(this)
                    .load(data2.url)
                    .into(binding_dialog.videoDetailDialogImage)
                builder.show()
            }

            "share" -> {
                /*
                초기에 받아온 데이터 객체에서 영상의 id를 뽑아서, 유튜브 와치 주소와 합쳐준다.
                인텐트 인스턴스를 생성하고, 각 속성을 정해준다.
                Intent().action은 인텐트의 목적을 정의한다. 우리가 설정한 값은 사용자가 선택한 정보를 제공하는 목적이다.
                Intent().putExtra(Intext.EXTRA_TEXT,"")는 어떤 정보를 제공할 지 데이터를 첨부한다. EXTRA_TEXT는 일반 텍스트 데이터를 의미한다.
                Intent().type은 인텐트가 다루는 데이터의 MIME 타입을 나타낸다. "text/plain"은 일반 텍스트 데이터를 나타낸다.

                Intent.createChooser() 인스턴스는 사용자에게 데이터를 처리할 수 있는 여러 앱 중 하나를 선택하도록 하는 대화 상자(선택 창)를 생성하게 한다.
                shareIntent.resolveActivity(packageManager)는, shareIntent를 처리할 수 있는 어플이 있는지 확인하고, 하나도 없으면 null을 반환한다.
                즉 처리할 수 있는어플이 하나라도 있으면, 데이터를 처리할 수 있는 여러 앱 중 하나를 선택하도록 하는 대화 상자(선택 창)를 StartActvitiy 하게 된다.
                 */
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + data2.videoid)
                    type = "text/plain"
                }
                Log.d(
                    "YouTubeProjects ",
                    "공유한 주소 : https://www.youtube.com/watch?v=" + "${data2.videoid}"
                )
                val chooser = Intent.createChooser(shareIntent, "공유할 앱을 골라주세요.")
                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                }
            }

            "like" -> {
                bt3state = !bt3state
                if (bt3state) {
                    MyPageFunc.saveVideo(data2)
                    binding.videoDetailFbt3.setImageResource(R.drawable.ic_video_detail_liked)
                } else {
                    MyPageFunc.deleteVideo(data2)
                    binding.videoDetailFbt3.setImageResource(R.drawable.ic_video_detail_disliked)
                }

            }

        }
    }
}
/*
문제점이나 해야할것들
[.xml]
1. com.google.android.material.floatingactionbutton.FloatingActionButton을 투명하게 만들기

android:background="@color/colorTransparent"
android:backgroundTint="@color/colorTransparent"
app:rippleColor="@color/colorTransparent"
app:borderWidth="0dp"
이렇게 주어도, 하얀색 작은 원이 남아있게 되고, 클릭시 그림자 효과가 생긴다.

그리고 이미지와 버튼의 위치가 맞지 않아서,
app:fabCustomSize="30dp"
app:maxImageSize="30dp" 를 추가하니 이미지가 맞춰졌다.

2. 플로팅 버튼의 elevation 값을 0dp를 줌으로써 그림자를 없앳는데, 높이가 0이되니 이미지가 버튼과 겹칠때 클릭이 안되는 문제 발생
 이때, android:outlineProvider="none" 을 주면 그림자가 비활성된다. 따라서 elevation값을 높게 줄 수 있고 버튼이 이미지보다 위에 있게 된다.

3. 이미지 둥근 사각형으로 만들기
shape를 통해 모서리를 깎은 둥근 사각형을 뒷배경으로 주려고 했으나 실패하였고,
이후 CardView 라이브러리를 추가하고, CardView 위젯을 통해 감싸주는 식으로 모서리가 깎인 사각형을 만들어주었다.

[다이어로그]
1. 정보 클릭시 정보가 나오게 하기
다이어 로그창으로 동영상에 대한 정보를 확인할 수 있다.
그런데, 이미지를 둥근 사각형으로 깎고 싶은데 잘 깎이지 않아 시간을 많이 허비했고, 실패하였다.

[액티비티 전환시 특별한 효과 주기]
1. 액티비티 진입시 애니메이션효과 나오게 하기
 overridePendingTransition(R.anim.anim_video_detail, R.id.video_detail_constraint)를 통해 가능하다.
 첫번째 인자는 어떻게 애니메이션 효과를 줄것인지에 대한 .xml 파일이고, 두번째 인자는 애니메이션 효과를 받을 .xml 파일이다.

 첫번째 인자의 경우 res > New > Android Resource Diretory , 그리고 Resource Type을 anmi로 하고
 이후 res/anim > New > Animation Resource File 를 통해 .xml 파일을 만든다.
 이후 <translate 위젯 에대해 여러가지 속성을 주면 된다.

 android:duration 는 애니메이션이 적용되는 시간(단위 : ms)
 android:fromXdelta 는 from은 시작위치, to는 끝 위치 X는 가로, Y는 세로방향
 즉 FX -100 FY 0 TX 0 TY 0이면
 시작위치는 화면 왼쪽 안보이는 지점에서 오른쪽으로 넘어온다.
 android:interpolator는 속도 변화를 제어한다.
 현재 준값은 처음과 끝은 천천히, 중간은 빠르게 전환되게끔 하는 값이다.

 */