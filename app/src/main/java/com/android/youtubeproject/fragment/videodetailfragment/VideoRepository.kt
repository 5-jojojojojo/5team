package com.android.youtubeproject.fragment.videodetailfragment

import android.util.Log
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkClient.apiService
import com.android.youtubeproject.api.model.DetailChannelModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.api.serverdata.DetailChannelData
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

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
    fun formatNumber(number: Long): String {
        return when {
            number < 1_000 -> number.toString()
            number < 1_000_000 -> "%.1fK".format(number / 1_000.0)
            number < 1_000_000_000 -> "%.1fM".format(number / 1_000_000.0)
            else -> "%.1fB".format(number / 1_000_000_000.0)
        }
    } fun formatNumber(number: Int): String {
        return when {
            number < 1_000 -> number.toString()
            number < 1_000_000 -> "%.1fK".format(number / 1_000.0)
            number < 1_000_000_000 -> "%.1fM".format(number / 1_000_000.0)
            else -> "%.1fB".format(number / 1_000_000_000.0)
        }
    }
    fun tag(tag: List<String>?): String {
        var tags:String = ""
        if ((tag != null)) {
            for (i in 0..tag?.size!! - 1) {
                tags += "[#" +tag[i] + "]  "
            }
        } else {tags = "태그가 존재하지 않는 영상입니다."}
        return tags
    }

}