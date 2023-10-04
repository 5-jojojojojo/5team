package com.android.youtubeproject.spf

import android.content.Context
import android.content.SharedPreferences
import com.android.youtubeproject.api.model.CategoryModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SPF(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("spf", Context.MODE_PRIVATE)

    fun saveTitle(title: String) {
        val editor = sharedPreferences.edit()
        editor.putString("title", title)

        editor.apply()
    }

    fun getTitle(): String {
        return sharedPreferences.getString("title", "") ?: ""
    }
}