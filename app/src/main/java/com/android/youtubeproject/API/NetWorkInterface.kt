package com.android.youtubeproject.API


import FavoritesData
import com.android.youtubeproject.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("videos?key=${Constants.Authorization}")
    fun getFavorites(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("videoCategoryId") videoCategoryId: String?
    ): Call<FavoritesData?>?

    @GET("videos?key=${Constants.Authorization}")
    suspend fun getNation(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("id") id: String
    )

    @GET("videos?key=${Constants.Authorization}")
    suspend fun getChannel(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("maxResults") maxResults: Int
    ): ChannelData
}
