package com.android.youtubeproject.viewmodel.nationviewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.api.serverdata.FavoritesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NationViewModel(private val apiService: NetWorkInterface) : ViewModel() {

    private val _nationResults = MutableLiveData<List<YoutubeModel>>()
    val nationResults: LiveData<List<YoutubeModel>> get() = _nationResults

    var nationItems: ArrayList<YoutubeModel> = ArrayList()
    var channelIdList: ArrayList<String> = ArrayList()
    var currentResults = 6

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    fun nationsServerResults(videoCategoryId: Int,maxResults:Int) {

        _isLoading.value = true
        nationItems.clear()
        channelIdList.clear()

        val nationKey = hashMapOf(
            "part" to "snippet,statistics,contentDetails",
            "chart" to "mostPopular",
            "maxResults" to maxResults.toString(),
            "regionCode" to "KR",
            "videoCategoryId" to videoCategoryId.toString()
        )
        apiService.getFavorites(nationKey)
            ?.enqueue(object : Callback<FavoritesData?> {
                override fun onResponse(
                    call: Call<FavoritesData?>,
                    response: Response<FavoritesData?>
                ) {
                    response.body()?.let {
                        for (items in response.body()!!.items) {
                            val id = items.id
                            val channelId = items.snippet.channelId
                            val title = items.snippet.title
                            val url = items.snippet.thumbnails.medium.url
                            val date = items.snippet.publishedAt
                            val description = items.snippet.localized.description
                            val channelname = items.snippet.channelTitle
                            val tags: List<String> = items.snippet.tags
                            val localtitle = items.snippet.localized.title
                            val viewCount = items.statistics.viewCount
                            val likeCount = items.statistics.likeCount
                            val favoriteCount = items.statistics.favoriteCount
                            val commentCount = items.statistics.commentCount
                            val definition = items.contentDetails.definition
                            nationItems.add(YoutubeModel( Constants.NATION_TYPE,id,channelId,title,url,date,description,channelname,tags,localtitle,viewCount,likeCount,favoriteCount,commentCount,definition))
                        }
                        for (item in nationItems) {
                            channelIdList.add(item.channelId)
                        }
                        Log.d("YouTubeProjects", "channelId(nationViewModel) : ${channelIdList}")
                    }
                    nationDataResults()
                }

                override fun onFailure(call: Call<FavoritesData?>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("YouTubeProjects", "에러 : ${t.message}")
                }
            })
    }

    private fun nationDataResults() {
        _nationResults.value = nationItems
        _isLoading.value = false
    }
}
