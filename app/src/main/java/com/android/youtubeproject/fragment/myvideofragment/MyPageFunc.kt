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

    /**
     * 상세 연동 없이 마이비디오에 테스트 데이터 입력하는 함수
     *
     * @param dataList YoutubeModel 상세에서 사용하는 데이타
     */
    fun testDummy(dataList: ArrayList<YoutubeModel>) {
        SharedPref.setString(App.instance.applicationContext, MY_VIDEO, convertToString(dataList.toMutableList()))
    }


    /**
     * 상세 연동 없이 마이비디오에 테스트 데이터 입력하는 함수
     *
     * @param context 컨텍스트
     * @param dataList YoutubeModel 상세에서 사용하는 데이타
     */
    fun testDummy(context: Context, dataList: MutableList<YoutubeModel>) {
        SharedPref.setString(context, MY_VIDEO, convertToString(dataList))
    }

    /**
     * SharedPreference에 마이비디오를 저장하는 함수
     * 저장 상태여부에 따라 토스트 발생
     *
     * @param data YoutubeModel
     */
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

    /**
     * SharedPreference에서 마이비디오를 삭제하는 함수
     * 삭제 여부에 따라 토스트 발생
     * @param data YoutubeModel
     */
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

    /**
     * SharedPreference에 해당 비디오가 저장되어 있는지 체크하는 함수
     *
     * @param data YoutubeModel
     * @return true 존재함
     */
    fun isExist(data: YoutubeModel): Boolean = isExist(data.id)

    /**
     * SharedPreference에 해당비디오가 존재하는지 체크하는 함수
     *
     * @param id 비디오 아이디
     * @return true 존재함
     */
    fun isExist(id: String): Boolean {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        val prevList: List<YoutubeModel> = convertToObject(prevSaveList)

        return prevList.any { it.id == id }
    }

    /**
     * SharedPreference에서 비디오 목록을 로드
     *
     * @return MutableList<YoutubeModel>
     */
    fun loadVideos(): MutableList<YoutubeModel> {
        val context = App.instance.applicationContext
        val prevSaveList = SharedPref.getString(context, MY_VIDEO, "")
        //가져온 목록을 리스트객체로 변환
        return convertToObject(prevSaveList)
    }

    /**
     * MutableList<YoutubeModel>를 입력받아서 String으로 전환하는 함수
     *
     * @param dataList MutableList<YoutubeModel>
     * @return JsonString
     */
    private fun convertToString(dataList: MutableList<YoutubeModel>): String {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
            .toJson(dataList)
    }

    /**
     * jsonString을 입력 받아서 MutableList<YoutubeModel>객체로 전환
     *
     * @param jsonStr jsonString
     * @return MutableList<YoutubeModel>
     */
    private fun convertToObject(jsonStr: String): MutableList<YoutubeModel> {
        return try {
            GsonBuilder().create().fromJson(jsonStr, Array<YoutubeModel>::class.java).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    /**
     * 입력받은 uri에 대한 읽기 권한이 영구적인지 체크하는 함수
     * 권한이 영구적이지 않으면 토스트 발생
     * 디폴트 유저 이미지의 경우 true로 리턴
     *
     * @param activity FragmentActivity
     * @param pictureUri uri
     * @return true 영구적인 권한을 취득한 상태 또는 디폴트 유저 이미지
     */
    fun hasPersistedUriPermissions(activity: FragmentActivity, pictureUri: Uri): Boolean {
//        Log.d("TEST", "hasPersistedUriPermissions ${++count}")
        if (pictureUri == getUri(R.drawable.ic_baseline_person_outline_24)) return true

        val persistedUriPermissions = activity.contentResolver.persistedUriPermissions
        val hasPermission = persistedUriPermissions.any { uriPermission ->
            uriPermission.uri == pictureUri
        }

        return hasPermission
    }

//    var count = 0
    fun getUri(resid: Int): Uri = Uri.parse("android.resource://" + R::class.java.`package`?.name + "/" + resid)
}