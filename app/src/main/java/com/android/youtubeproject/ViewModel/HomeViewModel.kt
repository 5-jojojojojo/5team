package com.android.youtubeproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.API.NetWorkInterface
import com.android.youtubeproject.API.YoutubeModel

class HomeViewModel(private val apiService:NetWorkInterface) : ViewModel() {
    private val _homeReselts = MutableLiveData<List<YoutubeModel>>()
    val homeResult:LiveData<List<YoutubeModel>> get() = _homeReselts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


}