package com.android.youtubeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.android.youtubeproject.viewpager2adapter.ViewPager2Adapter
import com.android.youtubeproject.databinding.ActivityMainBinding
import com.android.youtubeproject.fragment.myvideofragment.db.MyDatabase
import com.android.youtubeproject.fragment.myvideofragment.repository.MyVideoRepository
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), FragmentActionListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tabList = listOf("Home","Search", "Mypage")
    private val profileViewModel: MyVideoViewModel by viewModels {
        MyVideoViewModelFactory(MyVideoRepository(MyDatabase.getDatabase().getUser()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        profileViewModel //강제 초기화가 필요
        binding.viewPager2.adapter = ViewPager2Adapter(this)
        binding.viewPager2.setUserInputEnabled(false)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabList[position]
            when (position) {
                0 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_home_24)
                1 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_search_24)
                2 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_video_library_24)
            }
        }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            var currentState = 0
            var currentPosition = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING&&currentPosition == position){
                    if (currentPosition == 0) binding.viewPager2.currentItem =2
                    else if(currentPosition ==2) binding.viewPager2.currentItem =0
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        })
    }

    override fun onMyPageClicked() {
        binding.viewPager2.setCurrentItem(2, true)
    }

    override fun onSearchPageClicked() {
        binding.viewPager2.setCurrentItem(1, true)
    }

}