package com.android.youtubeproject.ViewModel.Home

import FavoritesData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.youtubeproject.API.NetWorkInterface
import com.android.youtubeproject.API.YoutubeModel
import com.android.youtubeproject.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val apiService:NetWorkInterface) : ViewModel() {
    private val _homeReselts = MutableLiveData<List<YoutubeModel>>()
    val homeResult:LiveData<List<YoutubeModel>> get() = _homeReselts
    var youtubeItems : ArrayList<YoutubeModel> = ArrayList()

    fun FavoritesResults(){
       apiService.getFavorites("snippet","mostPopular",10,"0")
           ?.enqueue(object : Callback<FavoritesData?> {
               override fun onResponse(call: Call<FavoritesData?>, response: Response<FavoritesData?>) {
                   response.body()?.let {
                       for(favorites in response.body()!!.items){
                           val title = favorites.snippet.title
                           val url = favorites.snippet.thumbnails.default.url
                           youtubeItems.add(YoutubeModel(Constants.FAVORITES_TYPE,title,url))
                           Log.d("YouTubeProjects", "Favorites데이터 : ${youtubeItems}")
                       }
                   }
                   HomeResult()
               }

               override fun onFailure(call: Call<FavoritesData?>, t: Throwable) {
                   Log.e("YouTubeProjects", "에러 : ${t.message}")
               }
           })
    }
    fun HomeResult(){
        _homeReselts.value = youtubeItems
    }
}