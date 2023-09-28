package com.android.youtubeproject.viewmodel.channelmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.NetWorkInterface

class ChannelViewModelFactory(private val apiService:NetWorkInterface): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelclass:Class<T>):T{
        return ChannelViewModel(apiService) as T
    }
}