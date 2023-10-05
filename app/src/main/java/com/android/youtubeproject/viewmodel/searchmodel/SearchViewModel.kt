package com.android.youtubeproject.viewmodel.searchmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.api.serverdata.FavoritesData
import com.android.youtubeproject.api.serverdata.SearchData
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val apiService:NetWorkInterface): ViewModel() {
    private val _searchResults = MutableLiveData<List<ChannelModel>>()
    val searchResults : LiveData<List<ChannelModel>> get() = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading

    var searchItems : ArrayList<ChannelModel> = ArrayList()
    companion object{var currentResults = 12}

    val youtubeItems : ArrayList<YoutubeModel> = ArrayList()
    private val _youtubeResults = MutableLiveData<YoutubeModel>()
    val youtubeResults : LiveData<YoutubeModel> get() = _youtubeResults
    fun SearchServerResults(q:String,videoCategoryId:String,maxResults:Int){
        _isLoading.value = true
        searchItems.clear()
        val searchKey = hashMapOf(
            "part" to "snippet",
            "maxResults" to maxResults.toString(),
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
                        val videoid = items.id.videoId
                        searchItems.add(ChannelModel(Constants.SEARCH_TYPE,title,url,videoid))
                    }
                    Log.d("YouTubeProjects", "검색 데이터1 : ${searchItems}")
                }
                searchDataResults()
            }

            override fun onFailure(call: retrofit2.Call<SearchData?>, t: Throwable) {
                _isLoading.value = false
                Log.e("YouTubeProjects", "에러 : ${t.message}")
            }
        })
    }
    private fun searchDataResults(){
        _searchResults.value = searchItems
        _isLoading.value = false
    }
    fun getvideodata(channelid: String) {
        Log.d("YouTubeProjects", "getvideodata 함수가 호출되었습니다.")
        Log.d("YouTubeProjects", channelid)
        apiService.getFavoritessearchfragment(
            Constants.Authorization,
            "snippet,statistics,contentDetails",
            channelid,
            1
        )
            ?.enqueue(object : Callback<FavoritesData?> {
                override fun onResponse(
                    call: retrofit2.Call<FavoritesData?>,
                    response: Response<FavoritesData?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (!it.items.isNullOrEmpty()) {
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

                                    if(youtubeItems.isNotEmpty()) {
                                        youtubeItems[0] = YoutubeModel(
                                            Constants.NATION_TYPE,
                                            id,
                                            channelId,
                                            title,
                                            url,
                                            date,
                                            description,
                                            channelname,
                                            tags,
                                            localtitle,
                                            viewcount,
                                            likecount,
                                            favoriteCount,
                                            commentcount,
                                            definition,
                                            videolength,
                                            videoPublishedDatetime,
                                            channeltitle,
                                            videowidth,
                                            videoheight
                                        )
                                    } else {
                                        youtubeItems.add(
                                            YoutubeModel(
                                                Constants.NATION_TYPE,
                                                id,
                                                channelId,
                                                title,
                                                url,
                                                date,
                                                description,
                                                channelname,
                                                tags,
                                                localtitle,
                                                viewcount,
                                                likecount,
                                                favoriteCount,
                                                commentcount,
                                                definition,
                                                videolength,
                                                videoPublishedDatetime,
                                                channeltitle,
                                                videowidth,
                                                videoheight
                                            )
                                        )
                                    }
                                    Log.d(
                                        "YouTubeProjects", "Favorites데이터 : ${
                                            youtubeItems[0]
                                                .definition
                                        }"
                                    )
                                }
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("YouTubeProjects", "API 에러: $errorBody")
                    }
                    youtubeDataResults()
                }

                override fun onFailure(call: retrofit2.Call<FavoritesData?>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("YouTubeProjects", "에러 : ${t.message}")
                }
            })
    }
    private fun youtubeDataResults(){
        _youtubeResults.value = youtubeItems[0]
    }
}