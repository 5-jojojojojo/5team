package com.android.youtubeproject.infinityscroll

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.spf.SharedPref
import com.android.youtubeproject.viewmodel.nationviewmodel.NationViewModel

class NationScrollListener(private val nationViewModel: NationViewModel,private val homeFragment:
HomeFragment, private val context:Context) :
    RecyclerView
    .OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val index = SharedPref.getIndex(context)

        if (homeFragment.nation_loading &&!recyclerView.canScrollHorizontally(1)) {
            Log.d("YouTubeProjects","스크롤리스너: ${homeFragment.categoryItems[index].id}?")
            NationViewModel.currentResults += 6
            nationViewModel.nationsServerResults(homeFragment.categoryItems[index].id.toInt(),
                NationViewModel.currentResults)
        }
    }
}