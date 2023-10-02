package com.android.youtubeproject.fragment.myvideofragment


import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.android.youtubeproject.App
import com.android.youtubeproject.R
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.spf.SharedPref
import com.google.gson.GsonBuilder
import java.lang.Exception

object MyPageFunc {
    private const val MY_VIDEO = "MY_VIDEO"         //실제 상세 내용

    // YoutubeModel 목록을 SharedPreferences에 저장하는 메서드
    fun testDummy(dataList: ArrayList<YoutubeModel>) {
        SharedPref.setString(App.instance.applicationContext, MY_VIDEO, convertToString(dataList.toMutableList()))
    }

    // YoutubeModel 목록을 SharedPreferences에 저장하는 메서드 (Context를 직접 지정할 수 있음)
    fun testDummy(context: Context, dataList: MutableList<YoutubeModel>) {
        SharedPref.setString(context, MY_VIDEO, convertToString(dataList))
    }

    // YoutubeModel을 내 보관함에 저장하는 메서드
    fun saveVideo(data: YoutubeModel) {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)

        if (prevList.isEmpty()) {
            // 저장된 목록이 없을 경우 새로운 목록을 생성하고 데이터를 추가
            val saveList = mutableListOf<YoutubeModel>()
            saveList.add(data)
            SharedPref.setString(context, MY_VIDEO, convertToString(saveList))

            Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
        } else {
            if (!prevList.any { it.id == data.id }) {
                // 동일한 ID를 가진 데이터가 없을 경우 데이터 추가
                prevList.add(data)
                Toast.makeText(context, "내 보관함에 저장.", Toast.LENGTH_SHORT).show()
            } else {
                // 동일한 ID를 가진 데이터가 이미 존재하는 경우
                Toast.makeText(context, "이미 저장되어 있습니다.", Toast.LENGTH_SHORT).show()
            }
            SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
        }

    }

    // YoutubeModel을 내 보관함에서 삭제하는 메서드
    fun deleteVideo(data: YoutubeModel) {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: MutableList<YoutubeModel> = convertToObject(prevSaveList)

        val isRemoved = prevList.removeIf { it.id == data.id }
        if (isRemoved) {
            // 데이터가 삭제되었을 경우 목록을 업데이트
            SharedPref.setString(context, MY_VIDEO, convertToString(prevList))
            Toast.makeText(context, "내 보관함에서 삭제.", Toast.LENGTH_SHORT).show()
        }
    }

    // 특정 ID를 가진 YoutubeModel이 내 보관함에 존재하는지 확인하는 메서드
    fun isExist(data: YoutubeModel): Boolean = isExist(data.id)

    // 특정 ID를 가진 YoutubeModel이 내 보관함에 존재하는지 확인하는 메서드
    fun isExist(id: String): Boolean {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: List<YoutubeModel> = convertToObject(prevSaveList)

        return prevList.any { it.id == id }
    }

    // SharedPreferences에서 저장된 YoutubeModel 목록을 불러오는 메서드
    fun loadVideos(): MutableList<YoutubeModel> {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        //가져온 목록을 리스트객체로 변환
        return convertToObject(prevSaveList)
    }

    // YoutubeModel 목록을 JSON 문자열로 변환하는 메서드
    private fun convertToString(dataList: MutableList<YoutubeModel>): String {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
            .toJson(dataList)
    }

    // JSON 문자열을 YoutubeModel 목록으로 변환하는 메서드
    private fun convertToObject(jsonStr: String): MutableList<YoutubeModel> {
        return try {
            GsonBuilder().create().fromJson(jsonStr, Array<YoutubeModel>::class.java).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // 주어진 Uri에 대한 지속적인 권한이 있는지 확인하는 메서드
    fun hasPersistedUriPermissions(activity: FragmentActivity, pictureUri: Uri): Boolean {
        val persistedUriPermissions = activity.contentResolver.persistedUriPermissions
        val hasPermission = persistedUriPermissions.any { uriPermission ->
            uriPermission.uri == pictureUri
        }

        if(!hasPermission) {
            // 권한이 없을 경우 메시지 표시
            Toast.makeText(activity, R.string.has_not_permission_dont_profile_img, Toast.LENGTH_SHORT).show()
        }
        return hasPermission
    }
}