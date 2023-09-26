package com.android.youtubeproject.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.youtubeproject.API.NetWorkClient
import com.android.youtubeproject.Fragment.HomeFragment.HomeFavoritesAdapter
import com.android.youtubeproject.ViewModel.Home.HomeViewModel
import com.android.youtubeproject.ViewModel.Home.HomeViewModelFactory
import com.android.youtubeproject.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeadapter: HomeFavoritesAdapter
    private lateinit var homeLayoutManager: LinearLayoutManager
    private val apiServiceInstance = NetWorkClient.apiService
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(apiServiceInstance) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        setupView()
        setupListeners()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
    private fun observeViewModel(){
        homeViewModel.homeResult.observe(viewLifecycleOwner){
            Log.d("YouTubeProjects", "프래그먼트 데이터 : ${it}")
            if(homeadapter !=null){
                Log.d("YouTubeProjects", "어댑터 정상작동?")
                homeadapter.items.addAll(it)
                Log.d("YouTubeProjects", "adapter.items에 뭐가 찍혀있는데?${homeadapter.items}")
                homeadapter.notifyDataSetChanged()

            } else Log.d("YouTubeProjects", "어댑터 널값?")
        }
    }

    private fun setupView() {
        homeLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        homeadapter= HomeFavoritesAdapter(requireContext())
        binding.homeRecyclerView1.apply {
            layoutManager = homeLayoutManager
            adapter = homeadapter
            setHasFixedSize(true)
        }

    }
    private fun setupListeners() {
        homeViewModel.FavoritesResults()
    }
}