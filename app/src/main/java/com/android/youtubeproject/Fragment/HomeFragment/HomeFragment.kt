package com.android.youtubeproject.HomeFragment

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.youtubeproject.API.NetWorkClient
import com.android.youtubeproject.Fragment.HomeFragment.HomeFragmentAdapter
import com.android.youtubeproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.http.Query


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var adapter : HomeFragmentAdapter
    private lateinit var homeLayoutManager : LinearLayoutManager
    private val apiServiceInstance = NetWorkClient.apiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupView(inflater,container)
        NetWork()
        return binding.root
    }
    private fun setupView(inflater: LayoutInflater, container: ViewGroup?){
        homeLayoutManager = LinearLayoutManager(context)
        binding.apply {
            homeRecyclerView.layoutManager = homeLayoutManager
        }
//        adapter= HomeFragmentAdapter(context)
    }
    private fun NetWork() = lifecycleScope.launch{
        val responseData = NetWorkClient.apiService.getVideo("snippet","mostPopular",10,"0")
        Log.d("YouTubeProjects", "데이터 : ${responseData}")
    }
}