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
import com.android.youtubeproject.MainActivity
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
    private lateinit var channeladapter: HomeChannelAdapter
    private val apiServiceInstance = NetWorkClient.apiService
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(apiServiceInstance) }
    private val categoryViewModel: CategoryViewModel by viewModels {CategoryViewModelFactory(apiServiceInstance)}
    private val nationViewModel: NationViewModel by viewModels {NationViewModelFactory(apiServiceInstance)}
    private val channelViewModel:ChannelViewModel by viewModels {ChannelViewModelFactory(apiServiceInstance)}
    var categoryItems = ArrayList<CategoryModel>()
    private var nation_loading = true
    private var channel_loading = true
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
        nationViewModel.apply {
            nationResults.observe(viewLifecycleOwner) {
                nationadapter.items.clear()
                nationadapter.items.addAll(it)
                channelViewModel.channelServerResults(nationViewModel.channelId)
                Log.d("YouTubeProjects", "channelId(프래그먼트) : ${nationViewModel.channelId}")
                nationadapter.notifyDataSetChanged()
            }
            isLoading.observe(viewLifecycleOwner){ isLoading ->
                binding.nationLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
                nation_loading = !isLoading
            }
        }
        channelViewModel.apply {
            channelResults.observe(viewLifecycleOwner){
                channeladapter.items.clear()
                channeladapter.items.addAll(it)
                channeladapter.notifyDataSetChanged()
            }
            isLoading.observe(viewLifecycleOwner){isLoading ->
                binding.channelLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
                channel_loading = !isLoading
            }
        }

    }

    private fun FavoritesView() {

        homeadapter = HomeFavoritesAdapter(requireContext())
        nationadapter = HomeNationAdapter(requireContext())
        channeladapter = HomeChannelAdapter(requireActivity())

        binding.apply {
            homeRecyclerView1.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = nationadapter
                setHasFixedSize(true)
            }
            homeRecyclerView3.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = channeladapter
                setHasFixedSize(true)
            }
            //myVideo이동
            ivMyVideo.setOnClickListener {
                (requireActivity() as MainActivity).onMyPageClicked()
            }
        }

    }

    private fun setupListeners() {
        homeViewModel.FavoritesResults()
        categoryViewModel.categoryServerResults()
    }
}