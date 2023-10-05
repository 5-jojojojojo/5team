package com.android.youtubeproject.infinityscroll

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetailViewModel

class CommentsScrollListener(private val videodetailviewmodel: VideoDetailViewModel) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {
            Log.d("YourTag", "스크롤리스너 호출되니?")
            videodetailviewmodel.maxResults +=10
            videodetailviewmodel.getComments(videodetailviewmodel.videoid.value!!)  // 댓글 로딩 함수 호출
        }
    }
}