package com.android.youtubeproject.infinityscroll

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModel

class FavoritesScrollListener(private val homeViewModel: HomeViewModel) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollHorizontally(1)) {
            Log.d("YouTubeProjects", "Favorites스크롤리스너 호출되니?")
            HomeViewModel.currentResults += 6
            homeViewModel.FavoritesResults(HomeViewModel.currentResults)
        }
    }
}