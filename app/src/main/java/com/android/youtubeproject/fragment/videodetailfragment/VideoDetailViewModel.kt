package com.android.youtubeproject.fragment.videodetailfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkClient
//import com.android.youtubeproject.api.model.CommentModel
import com.android.youtubeproject.api.model.DetailChannelModel
import com.android.youtubeproject.api.model.YoutubeModel
//import com.android.youtubeproject.api.serverdata.CommentThreadListResponse
import com.android.youtubeproject.api.serverdata.DetailChannelData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoDetailViewModel(Item: YoutubeModel,private val repository: VideoRepository) :
    ViewModel() {
    private val _moreButton = MutableLiveData<Boolean>().apply { value = true }
    val moreButton: LiveData<Boolean> get() = _moreButton

    private val _infoButton = MutableLiveData<Boolean>()
    val infoButton: LiveData<Boolean> get() = _infoButton
    private val _shareButton = MutableLiveData<Boolean>()
    val shareButton: LiveData<Boolean> get() = _shareButton
    private val _likeButton = MutableLiveData<Boolean>().apply { value = Item.like }
    val likeButton: LiveData<Boolean> get() = _likeButton

    private val _item = MutableLiveData<YoutubeModel>().apply { value = Item }
    val item: LiveData<YoutubeModel> get() = _item

    private val _videoId = MutableLiveData<String>().apply { value = Item.id }
    val videoId: LiveData<String> get() = _videoId

    lateinit var title:String
    lateinit var date:String
    lateinit var channelname:String
    lateinit var description:String
    lateinit var videoid:String
    lateinit var url:String
    var videowidth = 0
    var videoheight = 0
    lateinit var channelItems: ArrayList<DetailChannelModel?>
    lateinit var channelurl:String
    lateinit var channelTitle:String
    lateinit var channelsubscriberCount:String
    lateinit var channelviewCount:String
    lateinit var channelvideoCount:String
    lateinit var videocommentcount:String
    lateinit var videolikecount:String
    lateinit var videoviewcount:String
    lateinit var videotag:String
    var channelwidth = 0
    var channelheight = 0
//    lateinit var commentItems: ArrayList<CommentModel>
    fun formatVideoData(){
        title = item.value!!.title
        date = item.value!!.date?.substring(0, 10)?.replace("-", "/") ?: ""
        channelname = item.value!!.channelname
        if(item.value!!.description == null){
            description = "설명이 존재하지 않는 영상입니다."
        }
        description = item.value!!.description
        videoid = "https://www.youtube.com/watch?v=" + item.value!!.id
        url = item.value!!.url
        videowidth = item.value!!.videowidth
        videoheight = item.value!!.videoheight
        videocommentcount = repository.formatNumber(item.value!!.commentcount?.toInt() ?:0)
        videolikecount = repository.formatNumber(item.value!!.likecount?.toInt() ?: 0)
        videoviewcount = repository.formatNumber(item.value!!.viewcount.toInt())
        videotag = repository.tag(item.value!!.tags)
    }
    fun formatChannelData(){
        channelurl = channelItems[0]?.thumbnailsurl ?: ""
        channelwidth = channelItems[0]?.thumbnailswidth ?: 0
        channelheight = channelItems[0]?.thumbnailsheight ?: 0
        channelTitle = channelItems[0]?.title ?: ""
        channelsubscriberCount = repository.formatNumber(channelItems[0]?.subscriberCount!!)
        channelviewCount = repository.formatNumber(channelItems[0]?.viewCount!!)
        channelvideoCount = repository.formatNumber(channelItems[0]?.videoCount!!)
    }
    fun toggleButtonState(buttonType: String) {
        when (buttonType) {
            "more" -> {
                _moreButton.value = _moreButton.value?.let {
                    !it
                }
            }

            "info" -> {
                _infoButton.value = _infoButton.value?.let {
                    !it
                } ?: true
            }

            "share" -> {
                _shareButton.value = _shareButton.value?.let {
                    !it
                } ?: true
            }

            "like" -> {
                _likeButton.value = !_likeButton.value!!
                updatemyvideo(_item.value!!)
            }
        }
    }

    fun addData(data2: YoutubeModel) {
        _likeButton.value = repository.isExist(data2)
    }

    fun getHtml(videoId: String): String {
        return repository.getHtmlForVideo(videoId)
    }
    fun updatemyvideo(video:YoutubeModel){
        if (!_likeButton.value!!) {
            repository.deleteVideo(video)
        } else if(_likeButton.value!!){
            repository.saveVideo(video)
        }
    }
    fun getChannelData(){
        if(item.value?.channelId != null){
        getChannelData(item.value?.channelId!!)
        formatVideoData()}
    }
    fun getChannelData(channelid: String) {
        channelItems = ArrayList()
        NetWorkClient.apiService.getChanneldetailactivity(
            Constants.Authorization,
            "snippet,statistics",
            channelid,
            1
        )
            ?.enqueue(object : Callback<DetailChannelData?> {
                override fun onResponse(
                    call: Call<DetailChannelData?>,
                    response: Response<DetailChannelData?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (!it.items.isNullOrEmpty()) {
                                val title = it.items[0].snippet.title
                                val description = it.items[0].snippet.description
                                val publishedAt = it.items[0].snippet.publishedAt
                                val viewCount = it.items[0].statistics.viewCount
                                val subscriberCount = it.items[0].statistics.subscriberCount
                                val hiddenSubscriberCount =
                                    it.items[0].statistics.hiddenSubscriberCount
                                val videoCount = it.items[0].statistics.videoCount
                                val thumbnailsurl = it.items[0].snippet.thumbnails.high.url
                                val thumbnailswidth = it.items[0].snippet.thumbnails.high.width
                                val thumbnailsheight = it.items[0].snippet.thumbnails.high.height
                                channelItems.add(
                                    DetailChannelModel(
                                        title,
                                        description,
                                        publishedAt,
                                        viewCount,
                                        subscriberCount,
                                        hiddenSubscriberCount,
                                        videoCount,
                                        thumbnailsurl,
                                        thumbnailswidth,
                                        thumbnailsheight
                                    )
                                )
                                formatChannelData()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("YouTubeProjects", "API 에러: $errorBody")
                    }
                }
                override fun onFailure(call: Call<DetailChannelData?>, t: Throwable) {
                    Log.e("YouTubeProjects", "에러 : ${t.message}")
                }
            })
    }
//    fun getComments(videoId: String) {
//        val commentItems = ArrayList<CommentModel>()
//
//        NetWorkClient.apiService.getComments(videoId, "snippet,replies")
//            ?.enqueue(object : Callback<CommentThreadListResponse?> {
//                override fun onResponse(
//                    call: Call<CommentThreadListResponse?>,
//                    response: Response<CommentThreadListResponse?>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let {
//                            if (!it.items.isNullOrEmpty()) {
//                                for (item in it.items) {
//                                    val snippet = item.snippet.topLevelComment.snippet
//                                    val textOriginal = snippet.textOriginal
//                                    val authorDisplayName = snippet.authorDisplayName
//                                    val authorProfileImageUrl = snippet.authorProfileImageUrl
//                                    val likeCount = snippet.likeCount
//                                    val publishedAt = snippet.publishedAt
//
//                                    commentItems.add(
//                                        CommentModel(
//                                            textOriginal,
//                                            authorDisplayName,
//                                            authorProfileImageUrl,
//                                            likeCount,
//                                            publishedAt
//                                        )
//                                    )
//                                }
//                                // 여기서 commentItems를 처리. 예: UI 업데이트 등
//                            }
//                        }
//                    } else {
//                        val errorBody = response.errorBody()?.string()
//                        Log.e("YouTubeProjects", "API 에러: $errorBody")
//                    }
//                }
//
//                override fun onFailure(call: Call<CommentThreadListResponse?>, t: Throwable) {
//                    Log.e("YouTubeProjects", "에러 : ${t.message}")
//                }
//            })
//    }

}