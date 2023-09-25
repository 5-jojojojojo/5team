package com.android.youtubeproject.ViewPager2Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.youtubeproject.HomeFragment.HomeFragment
import com.android.youtubeproject.MyVideoFragment.MyVideoFragment
import com.android.youtubeproject.SearchFragment.SearchFragment

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