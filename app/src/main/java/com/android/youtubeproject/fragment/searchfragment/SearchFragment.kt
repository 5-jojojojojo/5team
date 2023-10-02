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
import com.android.youtubeproject.databinding.FragmentSearchBinding
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModel
import com.android.youtubeproject.viewmodel.searchmodel.SearchViewModelFactory


class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var searchAdapter : SearchFragmentAdapter
    private val apiServiceInstance = NetWorkClient.apiService
    private val searchViewModel : SearchViewModel by viewModels{SearchViewModelFactory(apiServiceInstance)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(!query.isNullOrEmpty()){
                        val searchQuery = query.trim()
                        Log.d("YouTubeProjects", "검색어 : ${searchQuery}")
                        setUpView()
                        setupListeners(searchQuery,"2")
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
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
        searchViewModel.searchResults.observe(viewLifecycleOwner){
            searchAdapter.items.addAll(it)
            searchAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpView(){
        searchAdapter = SearchFragmentAdapter(requireContext())

        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = searchAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners(q:String,videoCategoryId:String){
        searchViewModel.SearchServerResults(q,videoCategoryId)
    }
}