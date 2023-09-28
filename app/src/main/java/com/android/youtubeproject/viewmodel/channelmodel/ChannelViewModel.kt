package com.android.youtubeproject.viewmodel.channelmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.NationModel
import com.android.youtubeproject.api.serverdata.ChannelData
import com.android.youtubeproject.api.serverdata.FavoritesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelViewModel(private val apiService: NetWorkInterface) : ViewModel() {
    private val _channelResults = MutableLiveData<List<NationModel>>()
    val channelResults: LiveData<List<NationModel>> get() = _channelResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var channelItems: ArrayList<NationModel> = ArrayList()

    fun channelServerResults(idQueryMap:ArrayList<String>) {
       Log.d("YouTubeProjects", "channelServerResults에서 받아온 ID: ${idQueryMap}")


//        apiService.getChannel("snippet", apiQueryMap)?.enqueue(object
//            :Callback<ChannelData?>{
//            override fun onResponse(
//                call: Call<ChannelData?>,
//                response: Response<ChannelData?>
//            ) {
//                response.body()?.let { channelData ->
//                    val items = channelData.items
//
////                        val title = firstItem.snippet.title
////                        val url = firstItem.snippet.thumbnails.high.url
//
//                }
//            }
//
//            override fun onFailure(call: Call<ChannelData?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }
}