package com.android.youtubeproject.fragment.homefragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModel
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModelFactory
import com.android.youtubeproject.api.NetWorkClient
import com.android.youtubeproject.api.model.CategoryModel
import com.android.youtubeproject.databinding.FragmentHomeBinding
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetail
import com.android.youtubeproject.`interface`.ItemClick
import com.android.youtubeproject.viewmodel.categorymodel.CategoryViewModel
import com.android.youtubeproject.viewmodel.categorymodel.CategoryViewModelFactory
import com.google.gson.GsonBuilder


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeadapter: HomeFavoritesAdapter
    private lateinit var homeLayoutManager: LinearLayoutManager
    private val apiServiceInstance = NetWorkClient.apiService
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(apiServiceInstance) }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(
            apiServiceInstance
        )
    }
    var categoryItems = ArrayList<CategoryModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FavoritesView()
        setupListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        homeViewModel.homeResult.observe(viewLifecycleOwner) {
            Log.d("YouTubeProjects", "프래그먼트 데이터 : ${it}")
            if (homeadapter != null) {
                Log.d("YouTubeProjects", "어댑터 정상작동?")
                homeadapter.items.addAll(it)
                Log.d("YouTubeProjects", "adapter.items에 뭐가 찍혀있는데?${homeadapter.items}")
                homeadapter.notifyDataSetChanged()

            } else Log.d("YouTubeProjects", "어댑터 널값?")
        }
        categoryViewModel.categoryResults.observe(viewLifecycleOwner) {
            Log.d("YouTubeProjects", "스니펫 데이터 : ${it}")
            categoryItems.addAll(it)
            val items = ArrayList<String>()
            categoryItems.forEach {
                if (it.category != null) {
                    items.add(it.category)
                }
                binding.homeSpinner.setItems(items)
            }

        }

    }

    private fun FavoritesView() {
        homeLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeadapter = HomeFavoritesAdapter(requireContext())
        binding.homeRecyclerView1.apply {
            layoutManager = homeLayoutManager
            adapter = homeadapter.apply {
                itemClick = object : ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val intent = Intent(requireContext(), VideoDetail::class.java)
                        val gson = GsonBuilder().create()
                        val data = gson.toJson(homeadapter.items[position])
                        intent.putExtra("itemdata", data)
                        startActivity(intent)
                    }
                // 동규 추가 2. 클릭 리스너 구현하였습니다.

                }
            }
            setHasFixedSize(true)
        }

    }

    private fun setupListeners() {
        homeViewModel.FavoritesResults()
        categoryViewModel.categoryServerResults()
    }
}