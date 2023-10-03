package com.android.youtubeproject.infinityscroll

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.viewmodel.nationviewmodel.NationViewModel

class NationScrollListener(private val nationViewModel: NationViewModel,private val homeFragment: HomeFragment) :
    RecyclerView
    .OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (homeFragment.nation_loading &&!recyclerView.canScrollHorizontally(1)) {
            Log.d("YouTubeProjects","호출 되었니?")
            nationViewModel.currentResults += 6
            nationViewModel.nationsServerResults(homeFragment.id,nationViewModel.currentResults)
        }
    }
}