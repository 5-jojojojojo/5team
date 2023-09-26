package com.android.youtubeproject.api

import com.android.youtubeproject.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    val apiService :NetWorkInterface
        get() = instance.create(NetWorkInterface::class.java)

    private val instance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(Constants.YoutubeUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}