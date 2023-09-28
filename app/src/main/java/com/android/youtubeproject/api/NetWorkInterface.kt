package com.android.youtubeproject.api


import com.android.youtubeproject.api.serverdata.FavoritesData
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.serverdata.CategoryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("videos?key=${Constants.Authorization}")
    fun getFavorites(
        @QueryMap queryMap: HashMap<String, String>
    ): Call<FavoritesData?>?
    @GET("videoCategories?key=${Constants.Authorization}")
    fun getCategory(
        @Query("part") part: String,
        @Query("regionCode") chart: String
    ) : Call<CategoryData?>?

    @GET("channels?key=${Constants.Authorization}")
    suspend fun getChannel(
        @Query("part") part: String,
        @QueryMap idQueryMap: Map<String, String>,
        @Query("maxResults") maxResults: Int
    )
}
