package com.android.youtubeproject.infinityscroll

import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.viewmodel.nationviewmodel.NationViewModel

class NationScrollListener(private val nationViewModel: NationViewModel) : RecyclerView
    .OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollHorizontally(1)) {
            nationViewModel.currentResults += 6
            nationViewModel.nationsServerResults(HomeFragment().id,nationViewModel.currentResults)
        }
    }
}