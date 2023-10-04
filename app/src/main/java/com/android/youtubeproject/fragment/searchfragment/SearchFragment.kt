package com.android.youtubeproject.fragment.searchfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.youtubeproject.MainActivity
import com.android.youtubeproject.R
import com.android.youtubeproject.api.NetWorkClient
import com.android.youtubeproject.api.model.CategoryModel
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.databinding.FragmentSearchBinding
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.fragment.myvideofragment.MyPageFunc
import com.android.youtubeproject.fragment.myvideofragment.viewmodel.MyVideoViewModel
import com.android.youtubeproject.infinityscroll.SearchScrollListener
import com.android.youtubeproject.method.shortToast
import com.android.youtubeproject.spf.SharedPref
import com.android.youtubeproject.spf.SharedPref.getCategory
import com.android.youtubeproject.spf.SharedPref.getIndex
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModel
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModelFactory
import com.google.gson.GsonBuilder
import com.android.youtubeproject.fragment.videodetailfragment.VideoDetail
import com.android.youtubeproject.infinityscroll.FavoritesScrollListener
import com.android.youtubeproject.`interface`.ItemClick


class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var searchAdapter : SearchFragmentAdapter
    private val apiServiceInstance = NetWorkClient.apiService
    private val searchViewModel : SearchViewModel by viewModels{SearchViewModelFactory(apiServiceInstance)}
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val profileViewModel: MyVideoViewModel by activityViewModels()

    var searchQuery:String? = null
    var videoCategoryId:String? = null
    var videoCategory = ArrayList<CategoryModel>()
    var search_loading = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                //마이비디오로 이동
                (requireActivity() as MainActivity).onMyPageClicked()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        selectCategory()
        setUpView()
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(!query.isNullOrEmpty()){
                        searchQuery = query.trim()
                        Log.d("YouTubeProjects", "검색어 : ${searchQuery}")
                        setupListeners()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
    private fun observeViewModel(){
        videoCategory = getCategory(requireContext())
//        videoCategoryId = getIndex(requireContext()).toString()
        Log.d("YouTubeProjects","홈에서받아온 카테고리 : ${videoCategory}")
        val items = ArrayList<String>()
        videoCategory.forEach {
            if (it.category != null) {
                items.add(it.category)
            }
            binding.searchSpinner.setItems(items)
        }

        searchViewModel.apply {
            searchResults.observe(viewLifecycleOwner){
                searchAdapter.items.clear()
                searchAdapter.items.addAll(it)
                searchAdapter.notifyDataSetChanged()
                isLoading.observe(viewLifecycleOwner){isLoading ->
                    binding.searchLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
                    search_loading = !isLoading
                }
        }
        }
        searchViewModel.youtubeResults.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(), VideoDetail::class.java)
            val gson = GsonBuilder().create()
            val data = gson.toJson(it)
            intent.putExtra("itemdata", data)
            resultLauncher.launch(intent)
        }

        //유저정보가 갱신되면 앱바 프로필도 함께 갱신
        profileViewModel.getUserByIndex(0)
        profileViewModel.selectedUser.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                //해당 컨텐츠의 영구 권한 체크
                if (MyPageFunc.hasPersistedUriPermissions(requireActivity(), it.picture)) {
                    binding.ivMyVideoForSearch.setImageURI(it.picture)
                }
                binding.tvWelcome.text = it.nickname + "님 환영합니다!"

            }
        }
    }

    private fun setUpView(){
        searchAdapter = SearchFragmentAdapter(requireContext())

        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = searchAdapter.apply {
                itemClick = object : ItemClick {
                    override fun onClick(view: View, position: Int) {
                        Log.d("YouTubeProjects", "Item clicked at position: $position")
                        searchViewModel.getvideodata(items[position].videoid)
                    }

                }
            }
            addOnScrollListener(SearchScrollListener(searchViewModel,this@SearchFragment,requireContext()))
            setHasFixedSize(true)
        }

        binding.ivMyVideoForSearch.setOnClickListener {
            (requireActivity() as MainActivity).onMyPageClicked()
        }
    }

    private fun setupListeners(){
        Log.d("YouTubeProjects","검색 호출")
        if(!searchQuery.isNullOrEmpty()&& !videoCategoryId.isNullOrEmpty()){
            searchViewModel.SearchServerResults(searchQuery!!,videoCategoryId!!,searchViewModel.currentResults)
        } else if(videoCategoryId.isNullOrEmpty()){
            requireContext().shortToast("카테고리를 먼저 선택해 주세요!!")
        }else requireContext().shortToast("검색어를 입력해 주세요!!")
    }

    private fun selectCategory(){
        binding.searchSpinner.setOnSpinnerItemSelectedListener{ oldIndex, oldItem, newIndex, newItem: String ->

            for (item in videoCategory) {
                if (item.category == newItem) {
                    videoCategoryId = item.id
                }
            }
            setupListeners()
        }
    }
}