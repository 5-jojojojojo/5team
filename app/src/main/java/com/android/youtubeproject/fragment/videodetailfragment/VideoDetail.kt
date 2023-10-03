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
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.android.youtubeproject.fragment.myvideofragment.db.MyDatabase
import com.android.youtubeproject.fragment.myvideofragment.repository.MyVideoRepository
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModelFactory
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoDetail : AppCompatActivity() {
    private val binding by lazy { ActivityVideoDetailBinding.inflate(layoutInflater) }
    private val videoRepository by lazy { VideoRepository() }
    private val gson by lazy { Gson() }
    private val itemData: YoutubeModel by lazy {
        val dataString = intent.getStringExtra("itemdata")
        gson.fromJson(dataString, YoutubeModel::class.java)
    }
    private val viewModel: VideoDetailViewModel by viewModels {
        VideoDetailViewModelFactory(itemData, videoRepository)
    }
    private val webView: WebView by lazy { findViewById(R.id.webview) }
    private val profileViewModel: MyVideoViewModel by viewModels {
        MyVideoViewModelFactory(MyVideoRepository(MyDatabase.getDatabase().getUser()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
        binding.videoDetailButtonMore.setOnClickListener {
            viewModel.toggleButtonState("more")
        }
        binding.videoDetailButtonInfo.setOnClickListener {
            viewModel.toggleButtonState("info")
        }
        binding.videoDetailButtonShare.setOnClickListener {
            viewModel.toggleButtonState("share")
        }
        binding.videoDetailButtonLike.setOnClickListener {
            viewModel.toggleButtonState("like")
        }
        //myVideo이동
        binding.ivMyVideo.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    fun initialize() {
        overridePendingTransition(R.anim.anim_video_detail, R.id.video_detail_constraint)
        viewModel.formatVideoData()
        viewModel.item.observe(this) {
            viewModel.addData(viewModel.item.value!!)
        }
        viewModel.moreButton.observe(this) { btmState ->
            animateButtons(btmState)
        }
        viewModel.infoButton.observe(this) {
            showDialog()
        }

        viewModel.shareButton.observe(this) {
            shareVideoUrl()
        }
        viewModel.likeButton.observe(this) { bt3State ->
            binding.videoDetailButtonLike.run {
                if (bt3State) setImageResource(R.drawable.ic_video_detail_liked)
                else if (!bt3State) setImageResource(R.drawable.ic_video_detail_disliked)
            }
        }
        viewModel.videoId.observe(this) { videoId ->
            val html = viewModel.getHtml(videoId)
            webView.loadData(html, "text/html", "utf-8")
        }
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.WHITE)

        //유저정보가 갱신되면 앱바 프로필도 함께 갱신
        profileViewModel.getUserByIndex(0)
        profileViewModel.selectedUser.observe(this) { userData ->
            userData?.let {
                //해당 컨텐츠의 영구 권한 체크
                if (MyPageFunc.hasPersistedUriPermissions(this, it.picture)) {
                    binding.ivMyVideo.setImageURI(it.picture)
                }
            }
        }
    }


    fun animateButtons(btmState: Boolean) {
        lifecycleScope.launch {
            val delaytime = 50L
            if (!btmState) {
                binding.videoDetailButtonMore.hide()
                binding.videoDetailButtonInfo.show()
                delay(delaytime)
                binding.videoDetailButtonShare.show()
                delay(delaytime)
                binding.videoDetailButtonLike.show()
                delay(delaytime)
                binding.videoDetailButtonMore.show()
            } else if (btmState) {
                binding.videoDetailButtonMore.hide()
                binding.videoDetailButtonInfo.hide()
                delay(delaytime)
                binding.videoDetailButtonShare.hide()
                delay(delaytime)
                binding.videoDetailButtonLike.hide()
                delay(delaytime)
                binding.videoDetailButtonMore.show()
            }
        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val binding_dialog = DialogVideoDetailInformationBinding.inflate(layoutInflater)
        builder.setView(binding_dialog.root)
        binding_dialog.videoDetailDialogTextTitle.text = viewModel.title
        binding_dialog.videoDetailDialogTextDate.text = viewModel.date
        binding_dialog.videoDetailDialogTextChannelname.text = viewModel.channelname
        binding_dialog.videoDetailDialogTextContent.text = viewModel.description
        Glide.with(this)
            .load(viewModel.url)
            .into(binding_dialog.videoDetailDialogImage)
        builder.show()
    }

    fun shareVideoUrl() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                viewModel.item.value!!.id
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
1. MVVM패턴 적용하기 : Observe와 LiveData는 이해하고 코드를 돌아가게 만들수는 있으나, MVVM패턴이 맞는지 모르겠다.(적당히 하고 스킵)
2. 다이어로그 디자인하기 : 각 타이틀에 로고를 달아준다던지, 내용 뒤에 카드뷰를 넣어준다던지 등등 생각해보기.

4. 두번째 목록에 있는것도 클릭하면 상세페이지로 넘어가게끔 하기
5. 변수명 딱 보면 좋아요 버튼, 더보기 버튼, 정보 버튼, 공유 버튼 이런식으로 수정하기.
   나중에는 남이한 코드를 내가 하게되고, 내가 한 코드를 남이 보게 되는데 그럴때 잘 이해되도록 작성해야한다.
   주석같은거도 꼼꼼히 달아보고

 */