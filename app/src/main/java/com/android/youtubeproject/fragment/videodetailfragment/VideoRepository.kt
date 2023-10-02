package com.android.youtubeproject.fragment.videodetailfragment

import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc

class VideoRepository {
    fun isExist(video: YoutubeModel): Boolean {
        return MyPageFunc.isExist(video)
    }

    fun saveVideo(video: YoutubeModel) {
        MyPageFunc.saveVideo(video)
    }

    fun deleteVideo(video: YoutubeModel) {
        MyPageFunc.deleteVideo(video)
    }
}