package com.android.youtubeproject.infinityscroll

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.searchfragment.SearchFragment
import com.android.youtubeproject.method.shortToast
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModel

class SearchScrollListener(private val searchViewModel: SearchViewModel, private val
searchFragment: SearchFragment,private val context:Context) : RecyclerView
.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (searchFragment.search_loading&&!recyclerView.canScrollVertically(1)) {
            Log.d("YouTubeProjects","서치 무한스크롤")
            if(!searchFragment.searchQuery.isNullOrEmpty()&& !searchFragment.videoCategoryId
                    .isNullOrEmpty()) {
                searchViewModel.currentResults += 6
                searchViewModel.SearchServerResults(searchFragment.searchQuery!!,searchFragment
                    .videoCategoryId!!,
                    searchViewModel.currentResults
                )
            }else context.shortToast("카테고리를 먼저 선택해 주세요!!")
        }
    }
}