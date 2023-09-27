package com.android.youtubeproject.viewmodel.nationviewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.Constants
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
    var isFirst = true

    fun nationsServerResults(videoCategoryId: String) {
        nationItems.clear()
        apiService.getNations("snippet", "mostPopular", 20, "KR", videoCategoryId)
            ?.enqueue(object : Callback<FavoritesData?> {
                override fun onResponse(
                    call: Call<FavoritesData?>,
                    response: Response<FavoritesData?>
                ) {

                    response.body()?.let {

                        for (items in response.body()!!.items) {
                            val title = items.snippet.title
                            val url = items.snippet.thumbnails.default.url
                            nationItems.add(NationModel(Constants.NATION_TYPE, title, url))
                        }
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
