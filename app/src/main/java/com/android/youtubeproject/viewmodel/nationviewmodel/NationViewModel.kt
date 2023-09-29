package com.android.youtubeproject.viewmodel.nationviewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.HashMapData
import com.android.youtubeproject.api.NetWorkInterface
import com.android.youtubeproject.api.model.NationModel
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.api.serverdata.FavoritesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NationViewModel(private val apiService: NetWorkInterface) : ViewModel() {

    private val _nationResults = MutableLiveData<List<NationModel>>()
    val nationResults: LiveData<List<NationModel>> get() = _nationResults

    var nationItems: ArrayList<NationModel> = ArrayList()
    var channelId: ArrayList<String> = ArrayList()
    fun nationsServerResults(videoCategoryId: String) {
        nationItems.clear()
        channelId.clear()

        val nationKey = hashMapOf(
            "part" to "snippet",
            "chart" to "mostPopular",
            "maxResults" to "30",
            "regionCode" to "KR",
            "videoCategoryId" to videoCategoryId
        )
        apiService.getFavorites(nationKey)
            ?.enqueue(object : Callback<FavoritesData?> {
                override fun onResponse(
                    call: Call<FavoritesData?>,
                    response: Response<FavoritesData?>
                ) {
                    response.body()?.let {
                        for (items in response.body()!!.items) {
                            val title = items.snippet.title
                            val url = items.snippet.thumbnails.medium.url
                            val id = items.snippet.channelId
                            nationItems.add(NationModel(Constants.NATION_TYPE, title, url,id))
                        }
                        for (item in nationItems) {
                            channelId.add(item.id)
                        }
                        Log.d("YouTubeProjects", "channelId(nationViewModel) : ${channelId}")
                    }
                    nationDataResults()
                }

                override fun onFailure(call: Call<FavoritesData?>, t: Throwable) {
                    Log.e("YouTubeProjects", "에러 : ${t.message}")
                }
            })
    }

    private fun nationDataResults() {
        _nationResults.value = nationItems
    }
}
