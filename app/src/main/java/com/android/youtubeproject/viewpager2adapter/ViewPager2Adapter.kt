package com.android.youtubeproject.viewpager2adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.fragment.myvideofragment.MyVideoFragment
import com.android.youtubeproject.fragment.searchfragment.SearchFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()

            1 -> SearchFragment()

            else -> MyVideoFragment()
        }
    }

}