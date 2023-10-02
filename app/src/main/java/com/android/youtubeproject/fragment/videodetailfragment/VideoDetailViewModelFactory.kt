package com.android.youtubeproject.fragment.videodetailfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.youtubeproject.api.model.YoutubeModel

class VideoDetailViewModelFactory(val Item: YoutubeModel,private val repository: VideoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoDetailViewModel(Item,repository) as T
    }
}