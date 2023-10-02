package com.android.youtubeproject.fragment.videodetailfragment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.android.youtubeproject.R
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.ActivityVideoDetailBinding
import com.android.youtubeproject.databinding.DialogVideoDetailInformationBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoDetail : AppCompatActivity() {
    private val binding by lazy { ActivityVideoDetailBinding.inflate(layoutInflater) }
    private val videoRepository by lazy {VideoRepository()}
    private val viewModel: VideoDetailViewModel by viewModels { VideoDetailViewModelFactory(data2,videoRepository) }
    private val webView: WebView by lazy {findViewById(R.id.webview)}
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

    fun ButtonAction(buttontype: String) {
        when (buttontype) {
            "main" -> {
                viewModel.toggleButtonmState()
            }

            "infomation" -> {
                showDialog()
            }

            "share" -> {
                shareVideoUrl()
            }

            "like" -> {
                viewModel.toggleButton3State()
                viewModel.handleLikeAction(data2)
            }
        }
    }

    fun initialize() {
        overridePendingTransition(R.anim.anim_video_detail, R.id.video_detail_constraint)
        jsonToModel()
        viewModel.item.observe(this) { item ->
            viewModel.addData(data2)
        }
        viewModel.btmState.observe(this) { btmState ->
            animateButtons(btmState)
        }
        viewModel.bt3State.observe(this) { bt3State ->
            binding.videoDetailFbt3.run {
                if (bt3State) setImageResource(R.drawable.ic_video_detail_liked)
                else if (!bt3State) setImageResource(R.drawable.ic_video_detail_disliked)
            }
        }
        viewModel.videoId.observe(this) { videoId ->
            val html = viewModel.getHtmlForVideo(videoId)
            webView.loadData(html, "text/html", "utf-8")
        }

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.WHITE)
    }
    fun jsonToModel() {
        val data = intent.getStringExtra("itemdata")
        val gson = Gson()
        val type = object : TypeToken<YoutubeModel>() {}.type
        data2 = gson.fromJson(data, type)
    }

    fun animateButtons(btmState: Boolean) {
        lifecycleScope.launch {
            val delaytime = 50L
            if (!btmState) {
                binding.videoDetailFbtmain.hide()
                binding.videoDetailFbt1.show()
                delay(delaytime)
                binding.videoDetailFbt2.show()
                delay(delaytime)
                binding.videoDetailFbt3.show()
                delay(delaytime)
                binding.videoDetailFbtmain.show()
            } else if (btmState) {
                binding.videoDetailFbtmain.hide()
                binding.videoDetailFbt1.hide()
                delay(delaytime)
                binding.videoDetailFbt2.hide()
                delay(delaytime)
                binding.videoDetailFbt3.hide()
                delay(delaytime)
                binding.videoDetailFbtmain.show()
            }
        }
    }

    fun showDialog() {
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

    fun shareVideoUrl() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "https://www.youtube.com/watch?v=" + data2.videoid
            )
            type = "text/plain"
        }
        val chooser = Intent.createChooser(shareIntent, "공유할 앱을 골라주세요.")
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

}
/*
[해야될 일]
1. MVVM패턴 적용하기 : Observe와 LiveData는 이해하고 코드를 돌아가게 만들수는 있으나, MVVM패턴이 맞는지 모르겠다.
2. 다이어로그 디자인하기 : 각 타이틀에 로고를 달아준다던지, 내용 뒤에 카드뷰를 넣어준다던지 등등 생각해보기.
 */