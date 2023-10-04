package com.android.youtubeproject.viewmodel.homemodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.HashMapData
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.api.serverdata.FavoritesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val apiService: NetWorkInterface) : ViewModel() {
    private val _homeReselts = MutableLiveData<List<YoutubeModel>>()
    val homeResult: LiveData<List<YoutubeModel>> get() = _homeReselts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading
    var youtubeItems: ArrayList<YoutubeModel> = ArrayList()
    var currentResults = 6


    fun FavoritesResults(maxResults:Int) {
        youtubeItems.clear()
        _isLoading.value = true

        val favoritesKey = hashMapOf(
            "part" to "snippet,statistics,contentDetails",
            "chart" to "mostPopular",
            "maxResults" to maxResults.toString(),
            "videoCategoryId" to "0"
        )
        apiService.getFavorites(favoritesKey)
            ?.enqueue(object : Callback<FavoritesData?> {
                override fun onResponse(
                    call: Call<FavoritesData?>,
                    response: Response<FavoritesData?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if(!it.items.isNullOrEmpty()){
                                for (items in it.items) {
                                    val id = items.id
                                    val channelId = items.snippet.channelId
                                    val title = items.snippet.title
                                    val url = items.snippet.thumbnails.medium.url
                                    val date = items.snippet.publishedAt
                                    val description = items.snippet.localized.description
                                    val channelname = items.snippet.channelTitle
                                    val tags: List<String> = items.snippet.tags
                                    val localtitle = items.snippet.localized.title
                                    val viewcount = items.statistics.viewCount
                                    val likecount = items.statistics.likeCount
                                    val favoriteCount = items.statistics.favoriteCount
                                    val commentcount = items.statistics.commentCount
                                    val definition = items.contentDetails.definition
                                    val videolength = items.contentDetails.duration
                                    val videoPublishedDatetime = items.snippet.publishedAt
                                    val channeltitle = items.snippet.channelTitle
                                    val videowidth = items.snippet.thumbnails.medium.width
                                    val videoheight = items.snippet.thumbnails.medium.width

                                    youtubeItems.add(
                                        YoutubeModel( Constants.NATION_TYPE,id,channelId,title,url,date,description,channelname,tags,localtitle,viewcount,likecount,favoriteCount,commentcount,definition,videolength,videoPublishedDatetime,channeltitle,videowidth,videoheight))
                                    Log.d("YouTubeProjects", "Favorites데이터 : ${youtubeItems[0]
                                        .definition}")
                                }
                            }
                        }
                        HomeResult()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("YouTubeProjects", "API 에러: $errorBody")
                    }
                }
                override fun onFailure(call: Call<FavoritesData?>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("YouTubeProjects", "에러 : ${t.message}")
                }
            })
    }

    private fun HomeResult() {
        _homeReselts.value = youtubeItems
        _isLoading.value = false
    }

}