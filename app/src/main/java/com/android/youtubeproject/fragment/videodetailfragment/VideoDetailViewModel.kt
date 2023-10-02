package com.android.youtubeproject.fragment.videodetailfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc

class VideoDetailViewModel(Item: YoutubeModel, private val repository: VideoRepository) :
    ViewModel() {
    private val _btmState = MutableLiveData<Boolean>().apply { value = true }
    val btmState: LiveData<Boolean> get() = _btmState

    private val _bt3State = MutableLiveData<Boolean>().apply { value = false }
    val bt3State: LiveData<Boolean> get() = _bt3State

    private val _item = MutableLiveData<YoutubeModel>().apply { value = Item }
    val item: LiveData<YoutubeModel> get() = _item

    private val _videoId = MutableLiveData<String>().apply { value = Item.videoid }
    val videoId: LiveData<String> get() = _videoId

    fun toggleButtonmState() {
        val current = _btmState.value
        _btmState.value = !current!!
    }

    fun toggleButton3State() {
        val current = _bt3State.value
        _bt3State.value = !current!!
    }

    fun addData(data2: YoutubeModel) {
        _bt3State.value = repository.isExist(data2)
    }

    fun handleLikeAction(data2: YoutubeModel) {
        val bt3state = _bt3State.value!!
        if (bt3state) {
            repository.saveVideo(data2)
        } else if (!bt3state) {
            repository.deleteVideo(data2)
        }
    }

    fun getHtmlForVideo(videoId: String): String {
        return """
    <html>
    <body style="margin:0;padding:0;background:white">
        <div style="display:flex;justify-content:center;align-items:center;height:100%">
            <iframe width="100%" height="100%" src="https://www.youtube.com/embed/$videoId" frameborder="0" allowfullscreen></iframe>
        </div>
    </body>
    </html>
    """
    }
}