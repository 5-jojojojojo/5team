package com.android.youtubeproject.spf

import android.content.Context
import com.android.youtubeproject.api.model.CategoryModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Preference Util class
 * 자주 사용하는 타입의 기능을 구현해서 모아둠.
 */
object SharedPref {
    const val PREF_KEY = "YOUTUBE_PREF"

    fun setBoolean(context: Context?, key: String?, value: Boolean) {
        context ?: return

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    fun setInt(context: Context?, key: String?, value: Int) {
        context ?: return

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).apply()
    }

    fun setLong(context: Context?, key: String?, value: Long) {
        context ?: return

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putLong(key, value).apply()
    }

    fun setString(context: Context?, key: String?, value: String?) {
        context ?: return

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getBoolean(context: Context?, key: String?, defaultVal: Boolean): Boolean {
        context ?: return defaultVal

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defaultVal)
    }

    fun getInt(context: Context?, key: String?, defaultVal: Int): Int {
        context ?: return defaultVal

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getInt(key, defaultVal)
    }

    fun getLong(context: Context?, key: String?, defaultVal: Long): Long {
        context ?: return defaultVal

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getLong(key, defaultVal)
    }

    fun getString(context: Context?, key: String?, defaultVal: String): String {
        context ?: return defaultVal

        val sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getString(key, defaultVal) ?: ""
    }
    fun saveIndex(context:Context,newIndex:Int){
        val editor =context.getSharedPreferences("spf",Context.MODE_PRIVATE).edit()
        editor.putInt("newIndex",newIndex)
        editor.apply()
    }
    fun getIndex(context:Context):Int{
        val sharedPreferences =context.getSharedPreferences("spf",Context.MODE_PRIVATE)

        return sharedPreferences.getInt("newIndex",0)
    }
    fun saveCategory(context:Context,items :ArrayList<CategoryModel>) {
        val editor =context.getSharedPreferences("spf",Context.MODE_PRIVATE).edit()
        val gson = GsonBuilder().create()
        editor.putString("categoryItems", gson.toJson(items))
        editor.apply()
    }

    fun getCategory(context:Context): ArrayList<CategoryModel> {
        val sharedPreferences =context.getSharedPreferences("spf",Context.MODE_PRIVATE)
        val savedata = sharedPreferences.getString("categoryItems", "") ?:""
        val gson = Gson()
        val type = object : TypeToken<ArrayList<CategoryModel>>() {}.type
        return  if (savedata.isNotEmpty()) {
            gson.fromJson(savedata, type)
        } else {
            arrayListOf()
        }
    }
}