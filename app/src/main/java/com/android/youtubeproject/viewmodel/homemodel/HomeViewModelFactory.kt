package com.android.youtubeproject.viewmodel.homemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.NetWorkInterface

class HomeViewModelFactory(private val apiService : NetWorkInterface) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T{
        return HomeViewModel(apiService) as T
    }
}