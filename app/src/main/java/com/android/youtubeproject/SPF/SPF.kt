package com.android.youtubeproject.SPF

import android.content.Context
import android.content.SharedPreferences
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


    fun saveData(items :ArrayList<SearchData>) {
        val editor = sharedPreferences.edit()
        val gson = GsonBuilder().create()
        editor.putString("spf", gson.toJson(items))
        editor.apply()
    }

    fun getData(): ArrayList<SearchData> {
        val savedata = sharedPreferences.getString("spf", "") ?:""
        val gson = Gson()
        val type = object : TypeToken<ArrayList<SearchData>>() {}.type
        return  if (savedata.isNotEmpty()) {
            gson.fromJson(savedata, type)
        } else {
            arrayListOf()
        }
    }
}