package com.android.youtubeproject.Fragment.MyVideoFragment

import VideoItems
import android.content.Context
import android.widget.Toast
import com.android.youtubeproject.SPF.SharedPref
import com.google.gson.GsonBuilder
import java.lang.Exception

object MyPageFunc {
    private const val MY_FAVORITE = "MY_FAVORITE"

    fun testDummy(context: Context, dataList: MutableList<VideoItems>) {
        SharedPref.setString(context, MY_FAVORITE, convertToString(dataList))
    }

    fun saveVideo(context: Context, data: VideoItems) {

        val prevSaveList = SharedPref.getString(context, MY_FAVORITE, "")
        //가져온 목록을 리스트객체로 변환
        val prevList = convertToObject(prevSaveList)
        if (prevList.isEmpty()) {
            //리스트가 비어있으면 리스트생성 후 추가
            val saveList = mutableListOf<VideoItems>()
            saveList.add(data)
            SharedPref.setString(context, MY_FAVORITE, convertToString(saveList))

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
            SharedPref.setString(context, MY_FAVORITE, convertToString(prevList))
        }

    }

    fun loadVideos(context: Context): MutableList<VideoItems> {
        val prevSaveList = SharedPref.getString(context, MY_FAVORITE, "")
        //가져온 목록을 리스트객체로 변환
        return convertToObject(prevSaveList)
    }

    private fun convertToString(dataList: MutableList<VideoItems>): String {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
            .toJson(dataList)
    }

    private fun convertToObject(jsonStr: String): MutableList<VideoItems> {
        return try {
            GsonBuilder().create().fromJson(jsonStr, Array<VideoItems>::class.java).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}