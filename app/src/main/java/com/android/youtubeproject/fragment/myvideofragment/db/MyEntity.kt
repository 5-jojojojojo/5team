package com.android.youtubeproject.fragment.myvideofragment.db

import android.net.Uri
import androidx.room.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Entity(tableName = "user_table")
data class UserData(
    @PrimaryKey val index: Int,
    val id: String,
    val nickname: String,
    @TypeConverters(UriConverter::class)
    val picture: Uri
)

/**
 * UserData에서 Uri를 String컨버팅 하기 위한 클래스
 */
class UriConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return if (uriString == null) null else Uri.parse(uriString)
    }
}
