package com.android.youtubeproject.fragment.videodetailfragment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.youtubeproject.R
import com.android.youtubeproject.api.model.CommentModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.ActivityVideoDetailBinding
import com.android.youtubeproject.databinding.DialogVideoDetailInformationBinding
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.android.youtubeproject.fragment.myvideofragment.db.MyDatabase
import com.android.youtubeproject.fragment.myvideofragment.repository.MyVideoRepository
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModelFactory
import com.android.youtubeproject.infinityscroll.CommentsScrollListener
import com.android.youtubeproject.infinityscroll.FavoritesScrollListener
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
        Log.d("VideoDetail", "Received data: $dataString")
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
        viewModel.videoid.observe(this) {
            viewModel.getComments(it)
        }
        viewModel.channelid.observe(this) {
            viewModel.getChannelData(it)
        }
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
        showWebview()
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
        binding_dialog.videoDetailDialogVideotitle.text = viewModel.title
        binding_dialog.videoDetailDialogVideoUploadDate.text = viewModel.date
        binding_dialog.videoDetailDialogVideoViewCount2.text = viewModel.videoviewcount
        binding_dialog.videoDetailDialogVideoCommentCount2.text = viewModel.videocommentcount
        binding_dialog.videoDetailDialogVideoLikeCount2.text = viewModel.videolikecount
        Glide.with(this)
            .load(viewModel.url)
            .placeholder(R.drawable.ic_glide_loading)
            .error(R.drawable.ic_glide_error)
//            .override(viewModel.videowidth, viewModel.videoheight)
            .into(binding_dialog.videoDetailDialogVideoimage)
        Log.d("YouTube", viewModel.url)
        Glide.with(this)
            .load(viewModel.channelurl)
            .placeholder(R.drawable.ic_glide_loading)
            .error(R.drawable.ic_glide_error)
//            .override(viewModel.channelwidth, viewModel.channelheight)
            .into(binding_dialog.videoDetailDialogChannelimage)
        binding_dialog.videoDetailDialogChanneltitle.text = viewModel.channelTitle
        binding_dialog.videoDetailDialogChannelsubscribetext2.text =
            viewModel.channelsubscriberCount
        binding_dialog.videoDetailDialogChannelviewCounttext2.text = viewModel.channelviewCount
        binding_dialog.videoDetailDialogChannelvideoCounttext2.text = viewModel.channelvideoCount
        binding_dialog.videoDetailDialogVideotags2.text = viewModel.videotag
        binding_dialog.videoDetailDialogVideodescription2.text = viewModel.description
        binding_dialog.videoDetailRecyclerViewcomment.layoutManager =
            LinearLayoutManager(this@VideoDetail)
        binding_dialog.videoDetailRecyclerViewcomment.adapter =
            VideoDetailCommentAdapter(viewModel.commentitem.value ?: ArrayList<CommentModel>())
        binding_dialog.videoDetailRecyclerViewcomment.apply {
            addOnScrollListener(CommentsScrollListener(viewModel))
            setHasFixedSize(true)
        }
        viewModel.commentitem.observe(this) {
            binding_dialog.videoDetailRecyclerViewcomment.adapter?.notifyDataSetChanged()
        }
        binding_dialog.videoDetailDialogVideotagsimage.setOnClickListener {
            if (binding_dialog.videoDetailDialogVideotags2.visibility == View.GONE) {
                binding_dialog.videoDetailDialogVideotags2.visibility = View.VISIBLE
            } else {
                binding_dialog.videoDetailDialogVideotags2.visibility = View.GONE
            }
        }
        binding_dialog.videoDetailDialogVideodescriptionimage.setOnClickListener {
            if (binding_dialog.videoDetailDialogVideodescription2.visibility == View.GONE) {
                binding_dialog.videoDetailDialogVideodescription2.visibility = View.VISIBLE
            } else {
                binding_dialog.videoDetailDialogVideodescription2.visibility = View.GONE
            }
        }

        binding_dialog.videoDetailDialogVideoCommentCountimage.setOnClickListener {
            if (binding_dialog.videoDetailRecyclerViewcomment.visibility == View.GONE) {
                binding_dialog.videoDetailRecyclerViewcomment.visibility = View.VISIBLE
            } else {
                binding_dialog.videoDetailRecyclerViewcomment.visibility = View.GONE
            }
        }
        builder.show()
    }

    fun shareVideoUrl() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                viewModel.videoidurl
            )
            type = "text/plain"
        }
        val chooser = Intent.createChooser(shareIntent, "공유할 앱을 골라주세요.")
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    fun showWebview() {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.WHITE)
        lifecycleScope.launch {
            delay(1000)
            webView.loadData(viewModel.getHtml(viewModel.videoId.value!!), "text/html", "utf-8")
        }
    }

}
