package com.android.youtubeproject.infinityscroll

import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModel

class FavoritesScrollListener(private val homeViewModel: HomeViewModel) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollHorizontally(1)) {
            homeViewModel.currentResults += 6
            homeViewModel.FavoritesResults(homeViewModel.currentResults)
        }
    }
}