package com.android.youtubeproject.API

import retrofit2.http.GET
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("videos")
    suspend fun get(
        @Query("part") part:String,
//        @Query("")
//    @Query("")
    ) : VideoData
}