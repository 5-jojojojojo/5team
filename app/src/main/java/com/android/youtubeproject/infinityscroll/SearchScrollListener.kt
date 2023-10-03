package com.android.youtubeproject.infinityscroll

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.searchfragment.SearchFragment
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModel

class SearchScrollListener(private val searchViewModel: SearchViewModel, private val searchFragment: SearchFragment) : RecyclerView
.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (searchFragment.search_loading&&!recyclerView.canScrollVertically(1)) {
            Log.d("YouTubeProjects","검색스크롤리스너 호출되니? : ${searchViewModel.currentResults}")
            searchFragment.searchQuery?.let {
                searchViewModel.currentResults += 6
                searchViewModel.SearchServerResults(it,"2", searchViewModel.currentResults
                )
            }
        }
    }
}