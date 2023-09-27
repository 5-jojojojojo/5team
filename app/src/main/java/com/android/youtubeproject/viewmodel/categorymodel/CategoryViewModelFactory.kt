package com.android.youtubeproject.viewmodel.categorymodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.NetWorkInterface

class CategoryViewModelFactory(private val apiService: NetWorkInterface): ViewModelProvider.Factory {
    override fun <C : ViewModel> create(modelClass: Class<C>): C {
        return CategoryViewModel(apiService) as C
    }
}