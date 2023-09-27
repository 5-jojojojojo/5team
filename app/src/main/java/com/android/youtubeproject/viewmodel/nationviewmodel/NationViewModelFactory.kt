package com.android.youtubeproject.viewmodel.nationviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.viewmodel.homemodel.HomeViewModel

class NationViewModelFactory(private val apiService: NetWorkInterface): ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modeClass: Class<T>) :T{
        return NationViewModel(apiService) as T
    }
}
