package com.android.youtubeproject.fragment.myvideofragment


import android.content.Context
import android.widget.Toast
import com.android.youtubeproject.App
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.spf.SharedPref
import com.google.gson.GsonBuilder
import java.lang.Exception

object MyPageFunc {
    private const val MY_VIDEO = "MY_VIDEO"         //실제 상세 내용

    fun testDummy(dataList: ArrayList<YoutubeModel>) {
        SharedPref.setString(App.instance.applicationContext, MY_VIDEO, convertToString(dataList.toMutableList()))
    }

    fun testDummy(context: Context, dataList: MutableList<YoutubeModel>) {
        SharedPref.setString(context, MY_VIDEO, convertToString(dataList))
    }

    fun saveVideo(data: YoutubeModel) {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)

        if (prevList.isEmpty()) {
            val saveList = mutableListOf<YoutubeModel>()
            saveList.add(data)
            SharedPref.setString(context, MY_VIDEO, convertToString(saveList))

            Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
        } else {
            if (!prevList.any { it.id == data.id }) {
                prevList.add(data)
                Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "이미 저장되어 있습니다.", Toast.LENGTH_SHORT).show()
            }
            SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
        }

    }

    fun deleteVideo(data: YoutubeModel) {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)

        val isRemoved = prevList.removeIf { it.id == data.id }
        if (isRemoved) {
            SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
            Toast.makeText(context, "내 보관함에서 삭제.", Toast.LENGTH_SHORT).show()
        }
    }

    fun isExist(data: YoutubeModel): Boolean = isExist(data.id)

    fun isExist(id: String): Boolean {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: List<YoutubeModel> = convertToObject(prevSaveList)

        return prevList.any { it.id == id }
    }

    fun loadVideos(): MutableList<YoutubeModel> {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        //가져온 목록을 리스트객체로 변환
        return convertToObject(prevSaveList)
    }

    private fun convertToString(dataList: MutableList<YoutubeModel>): String {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
            .toJson(dataList)
    }

    private fun convertToObject(jsonStr: String): MutableList<YoutubeModel> {
        return try {
            GsonBuilder().create().fromJson(jsonStr, Array<YoutubeModel>::class.java).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}