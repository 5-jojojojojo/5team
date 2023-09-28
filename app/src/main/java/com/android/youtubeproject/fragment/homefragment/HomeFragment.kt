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
import com.android.youtubeproject.api.model.NationModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.FragmentHomeBinding
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetail
import com.android.youtubeproject.`interface`.ItemClick
import com.android.youtubeproject.viewmodel.categorymodel.CategoryViewModel
import com.android.youtubeproject.viewmodel.categorymodel.CategoryViewModelFactory
import com.android.youtubeproject.viewmodel.channelmodel.ChannelViewModel
import com.android.youtubeproject.viewmodel.channelmodel.ChannelViewModelFactory
import com.android.youtubeproject.viewmodel.nationviewmodel.NationViewModel
import com.android.youtubeproject.viewmodel.nationviewmodel.NationViewModelFactory
import com.google.gson.GsonBuilder


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeadapter: HomeFavoritesAdapter
    private lateinit var nationadapter: HomeNationAdapter
    private lateinit var homeLayoutManager: LinearLayoutManager
    private lateinit var nationLayoutManager: LinearLayoutManager
    private val apiServiceInstance = NetWorkClient.apiService
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(apiServiceInstance) }
    private val categoryViewModel: CategoryViewModel by viewModels {CategoryViewModelFactory(apiServiceInstance)}
    private val nationViewModel: NationViewModel by viewModels {NationViewModelFactory(apiServiceInstance)}
    private val channelViewModel:ChannelViewModel by viewModels {ChannelViewModelFactory(apiServiceInstance)}
    var categoryItems = ArrayList<CategoryModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        FavoritesView()
        setupListeners()

        binding.homeSpinner.setOnSpinnerItemSelectedListener { oldIndex, oldItem, newIndex, newItem: String ->
            var id = ""
            for (item in categoryItems) {
                if (item.category == newItem) {
                    id = item.id
                }
            }

            nationViewModel.nationsServerResults(id)

        }

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
        nationViewModel.nationResults.observe(viewLifecycleOwner) {
            nationadapter.items.clear()
            nationadapter.items.addAll(it)
            channelViewModel.channelServerResults(nationViewModel.channelId)
            Log.d("YouTubeProjects", "channelId(프래그먼트) : ${nationViewModel.channelId}")
            nationadapter.notifyDataSetChanged()
        }

    }

    private fun FavoritesView() {
        homeLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        nationLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeadapter = HomeFavoritesAdapter(requireContext())
        nationadapter = HomeNationAdapter(requireContext())
        binding.apply {
            homeRecyclerView1.apply {
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

                    }
                }
                setHasFixedSize(true)
            }
            homeRecyclerView2.apply {
                layoutManager = nationLayoutManager
                adapter = nationadapter

            }
        }

    }

    private fun setupListeners() {
        homeViewModel.FavoritesResults()
        categoryViewModel.categoryServerResults()
//        nationViewModel.nationsServerResults(categoryViewModel.categoryId.toString())
//        Log.d("YouTubeProjects", "두번째 탭 데이터 : ${categoryViewModel.categoryId}")
    }
}