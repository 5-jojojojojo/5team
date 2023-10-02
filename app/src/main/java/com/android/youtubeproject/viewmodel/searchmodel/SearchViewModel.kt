package com.android.youtubeproject.viewmodel.searchmodel

import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.api.serverdata.SearchData
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val apiService:NetWorkInterface): ViewModel() {
    private val _searchResults = MutableLiveData<List<ChannelModel>>()
    val searchResults : LiveData<List<ChannelModel>> get() = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading

    var searchItems : ArrayList<ChannelModel> = ArrayList()

    fun SearchServerResults(q:String,videoCategoryId:String){
        val searchKey = hashMapOf(
            "part" to "snippet",
            "maxResults" to "10",
            "q" to q,
            "type" to "video",
            "videoCategoryId" to videoCategoryId
        )
        apiService.getSearch(searchKey)?.enqueue(object : Callback<SearchData?>{
            override fun onResponse(
                call: retrofit2.Call<SearchData?>,
                response: Response<SearchData?>
            ) {
                response.body()?.let{
                    for(items in response.body()!!.items){
                        val title = items.snippet.title
                        val url = items.snippet.thumbnails.medium.url
                        searchItems.add(ChannelModel(Constants.SEARCH_TYPE,title,url))
                    }
                    Log.d("YouTubeProjects", "검색 데이터 : ${searchItems}")
                }
                searchDataResults()
            }

            override fun onFailure(call: retrofit2.Call<SearchData?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun searchDataResults(){
        _searchResults.value = searchItems
    }
}