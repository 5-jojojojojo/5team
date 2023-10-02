package com.android.youtubeproject.fragment.videodetailfragment

import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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