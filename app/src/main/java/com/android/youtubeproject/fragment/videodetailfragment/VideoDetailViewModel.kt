package com.android.youtubeproject.fragment.videodetailfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.api.model.YoutubeModel

class VideoDetailViewModel(Item: YoutubeModel, private val repository: VideoRepository) :
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
    fun formatVideoData(){
        val videoData = item.value
        title = "영상 제목 : " + videoData?.title
        date = "게시 날짜 : " + videoData?.date?.substring(0, 10)?.replace("-", "/")
        channelname ="채널 명 : " + videoData?.channelname
        description = "영상 설명 : " + videoData?.description
        videoid = "https://www.youtube.com/watch?v=" + videoData?.id
        url = item.value!!.url
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
}