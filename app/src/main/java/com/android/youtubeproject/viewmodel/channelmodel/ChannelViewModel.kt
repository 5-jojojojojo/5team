package com.android.youtubeproject.viewmodel.channelmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.api.serverdata.ChannelData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelViewModel(private val apiService: NetWorkInterface) : ViewModel() {
    private val _channelResults = MutableLiveData<List<ChannelModel>>()
    val channelResults: LiveData<List<ChannelModel>> get() = _channelResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var channelItems: ArrayList<ChannelModel> = ArrayList()

    fun channelServerResults(idQueryMap:ArrayList<String>) {
//       Log.d("YouTubeProjects", "channelServerResults에서 받아온 ID: ${idQueryMap}")
        channelItems.clear()

        _isLoading.value = true

        val channelKey = hashMapOf(
            "part" to "snippet",
            "id" to idQueryMap.joinToString(",")
        )
        apiService.getChannel(channelKey)?.enqueue(object
            :Callback<ChannelData?>{
            override fun onResponse(
                call: Call<ChannelData?>,
                response: Response<ChannelData?>
            ) {
                response.body()?.let { channelData ->
                    if (!channelData.items.isNullOrEmpty()){
                        for (items in channelData.items){
                            val title = items.snippet.title
                            val url = items.snippet.thumbnails.high.url
                            channelItems.add(ChannelModel(Constants.CHANNEL_TYPE,title,url))
                            Log.d("YouTubeProjects","ChannelViewModel Data : ${channelItems}")
                        }
                    }
                }
                channelDataResults()
            }

            override fun onFailure(call: Call<ChannelData?>, t: Throwable) {
                Log.e("YouTubeProjects", "에러 : ${t.message}")
                _isLoading.value = false
            }

        })
    }
    private fun channelDataResults() {
        _channelResults.value = channelItems
        _isLoading.value = false
    }
}