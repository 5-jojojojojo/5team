package com.android.youtubeproject.viewmodel.searchmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.NetWorkInterface

class SearchViewModelFactory(private val apiService:NetWorkInterface): ViewModelProvider.Factory {
    override fun<T : ViewModel> create(modelClass:Class<T>):T{
        return SearchViewModel(apiService) as T
    }
}