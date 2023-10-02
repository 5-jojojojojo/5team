package com.android.youtubeproject.fragment.videodetailfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.api.model.YoutubeModel

class VideoDetailViewModel(Item: YoutubeModel, private val repository: VideoRepository) :
    ViewModel() {
    private val _btmState = MutableLiveData<Boolean>().apply { value = true }
    val btmState: LiveData<Boolean> get() = _btmState

    private val _bt1State = MutableLiveData<Boolean>()
    val bt1State: LiveData<Boolean> get() = _bt1State
    private val _bt2State = MutableLiveData<Boolean>()
    val bt2State: LiveData<Boolean> get() = _bt2State
    private val _bt3State = MutableLiveData<Boolean>().apply { value = Item.like }
    val bt3State: LiveData<Boolean> get() = _bt3State

    private val _item = MutableLiveData<YoutubeModel>().apply { value = Item }
    val item: LiveData<YoutubeModel> get() = _item

    private val _videoId = MutableLiveData<String>().apply { value = Item.videoid }
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
        videoid = "https://www.youtube.com/watch?v=" + videoData?.videoid
        url = item.value!!.url
    }
    fun toggleButtonState(buttonType: String) {
        when (buttonType) {
            "m" -> {
                _btmState.value = _btmState.value?.let {
                    !it
                }
            }

            "1" -> {
                _bt1State.value = _bt1State.value?.let {
                    !it
                } ?: true
            }

            "2" -> {
                _bt2State.value = _bt2State.value?.let {
                    !it
                } ?: true
            }

            "3" -> {
                _bt3State.value = !_bt3State.value!!
                updatemyvideo(_item.value!!)
            }
        }
    }

    fun addData(data2: YoutubeModel) {
        _bt3State.value = repository.isExist(data2)
    }

    fun getHtml(videoId: String): String {
        return repository.getHtmlForVideo(videoId)
    }
    fun updatemyvideo(video:YoutubeModel){
        if (!_bt3State.value!!) {
            repository.deleteVideo(video)
        } else if(_bt3State.value!!){
            repository.saveVideo(video)
        }
    }
}