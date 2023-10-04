package com.android.youtubeproject.fragment.searchfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.youtubeproject.R
import com.android.youtubeproject.api.NetWorkClient
import com.android.youtubeproject.api.model.CategoryModel
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.databinding.FragmentSearchBinding
import com.android.youtubeproject.fragment.homefragment.HomeFragment
import com.android.youtubeproject.infinityscroll.SearchScrollListener
import com.android.youtubeproject.method.shortToast
import com.android.youtubeproject.spf.SharedPref
import com.android.youtubeproject.spf.SharedPref.getCategory
import com.android.youtubeproject.spf.SharedPref.getIndex
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModel
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModelFactory


class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var searchAdapter : SearchFragmentAdapter
    private val apiServiceInstance = NetWorkClient.apiService
    private val searchViewModel : SearchViewModel by viewModels{SearchViewModelFactory(apiServiceInstance)}

    var searchQuery:String? = null
    var videoCategoryId:String? = null
    var videoCategory = ArrayList<CategoryModel>()
    var search_loading = false

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
    }

    private fun setUpView(){
        searchAdapter = SearchFragmentAdapter(requireContext())

        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = searchAdapter
            addOnScrollListener(SearchScrollListener(searchViewModel,this@SearchFragment,requireContext()))
            setHasFixedSize(true)
        }
    }

    private fun setupListeners(){
        Log.d("YouTubeProjects","검색 호출")
        if(!searchQuery.isNullOrEmpty()&& !videoCategoryId.isNullOrEmpty()){
            searchViewModel.SearchServerResults(searchQuery!!,videoCategoryId!!,searchViewModel
                .currentResults)
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