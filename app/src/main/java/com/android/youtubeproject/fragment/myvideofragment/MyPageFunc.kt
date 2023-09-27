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
        //가져온 목록을 리스트객체로 변환
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)
        if (prevList.isEmpty()) {
            //리스트가 비어있으면 리스트생성 후 추가
            val saveList = mutableListOf<YoutubeModel>()
            saveList.add(data)
            SharedPref.setString(context, MY_VIDEO, convertToString(saveList))

            Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
        } else {
            //리스트가 비이있지 않으면
            if (!prevList.contains(data)) {
                //중복체크 후, 중복이 아니면 추가
                prevList.add(data)
                Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
            } else {
                //중복이면 중복 토스트 표시
                Toast.makeText(context, "이미 저장되어 있습니다.", Toast.LENGTH_SHORT).show()
            }
            //추가한 목록을 저장한다
            SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
        }

    }

    fun deleteVideo(data: YoutubeModel) {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        //가져온 목록을 리스트객체로 변환
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)
        if (prevList.isNotEmpty()) {
            if (prevList.remove(data)) {
                SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
                Toast.makeText(context, "내 보관함에서 삭제.", Toast.LENGTH_SHORT).show()
            }
        }
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