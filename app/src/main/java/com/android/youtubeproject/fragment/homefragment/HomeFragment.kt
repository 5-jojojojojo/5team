package com.android.youtubeproject.fragment.homefragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.youtubeproject.MainActivity
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModel
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModelFactory
import com.android.youtubeproject.api.NetWorkClient
import com.android.youtubeproject.api.model.CategoryModel
import com.android.youtubeproject.databinding.FragmentHomeBinding
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetail
import com.android.youtubeproject.infinityscroll.FavoritesScrollListener
import com.android.youtubeproject.infinityscroll.NationScrollListener
import com.android.youtubeproject.`interface`.ItemClick
import com.android.youtubeproject.spf.SharedPref
import com.android.youtubeproject.spf.SharedPref.saveCategory
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
    private val profileViewModel: MyVideoViewModel by activityViewModels()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    var categoryItems = ArrayList<CategoryModel>()
    var nation_loading = false
    private var channel_loading = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setUpView()
        setupListeners()

        binding.homeSpinner.setOnSpinnerItemSelectedListener { oldIndex, oldItem, newIndex, newItem: String ->
            var id = 0
            for (item in categoryItems) {
                if (item.category == newItem) {
                    id = item.id.toInt()
                }
            }
            nationViewModel.nationsServerResults(id, nationViewModel.currentResults)
            SharedPref.saveIndex(requireContext(),newIndex)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        homeViewModel.apply {
            homeResult.observe(viewLifecycleOwner) {
                homeadapter.items.clear()
                homeadapter.items.addAll(it)
                homeadapter.notifyDataSetChanged()
                isLoading.observe(viewLifecycleOwner){isLoading ->
                    binding.favoritesLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
                }
        }
    }
        categoryViewModel.categoryResults.observe(viewLifecycleOwner) {
            Log.d("YouTubeProjects", "스니펫 데이터 : ${it}")
            categoryItems.addAll(it)
            saveCategory(requireContext(), categoryItems)
            val items = ArrayList<String>()
            categoryItems.forEach {
                if (it.category != null) {
                    items.add(it.category)
                }
                binding.homeSpinner.setItems(items)
            }
            val getIndex = SharedPref.getIndex(requireContext())
            binding.homeSpinner.selectItemByIndex(getIndex)
            nationadapter.notifyDataSetChanged()
            channeladapter.notifyDataSetChanged()
        }
        nationViewModel.apply {
            nationResults.observe(viewLifecycleOwner) {
                nationadapter.items.clear()
                nationadapter.items.addAll(it)
                channelViewModel.channelServerResults(nationViewModel.channelIdList)
                Log.d("YouTubeProjects", "channelId(프래그먼트) : ${nationViewModel.channelIdList}")
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

        //유저정보가 갱신되면 앱바 프로필도 함께 갱신
        profileViewModel.getUserByIndex(0)
        profileViewModel.selectedUser.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                //해당 컨텐츠의 영구 권한 체크
                if (MyPageFunc.hasPersistedUriPermissions(requireActivity(), it.picture)) {
                    binding.ivMyVideo.setImageURI(it.picture)
                }
            }
        }

    }

    private fun setUpView() {

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
                            resultLauncher.launch(intent)
                        }

                    }
                }
                addOnScrollListener(FavoritesScrollListener(homeViewModel))
                setHasFixedSize(true)
            }
            homeRecyclerView2.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = nationadapter.apply {
                    itemClick = object : ItemClick{
                        override fun onClick(view: View, position: Int) {
                            val intent = Intent(requireContext(), VideoDetail::class.java)
                            val gson = GsonBuilder().create()
                            val data = gson.toJson(nationadapter.items[position])
                            intent.putExtra("itemdata", data)
                            resultLauncher.launch(intent)
                        }
                    }
                }
                addOnScrollListener(NationScrollListener(nationViewModel,this@HomeFragment))
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

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                //마이비디오로 이동
                (requireActivity() as MainActivity).onMyPageClicked()
            }
        }

    }

    private fun setupListeners() {
        homeViewModel.FavoritesResults(homeViewModel.currentResults)
        categoryViewModel.categoryServerResults()
    }
}