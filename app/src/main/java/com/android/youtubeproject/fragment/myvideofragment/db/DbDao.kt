package com.android.youtubeproject.fragment.myvideofragment.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyVideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  // INSERT, key 충돌이 나면 새 데이터로 교체
    suspend fun insertMyVideo(video: MyFavoriteVideo)

    @Query("SELECT * FROM my_video_table")
    /*suspend*/ fun getAllMyVideos(): LiveData<List<MyFavoriteVideo>>  // LiveData<> 를 사용함으로 List Student이 변동 있을 때마다 리턴해줌

    @Delete
    suspend fun deleteMyVideo(video: MyFavoriteVideo)

}


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    /*suspend*/ fun insert(userData: UserData)

    @Delete
    /*suspend*/ fun delete(userData: UserData)

    @Query("SELECT * FROM user_table WHERE id = :id")
    /*suspend*/ fun getUserById(id: String): UserData?

    @Query("SELECT * FROM user_table")
    /*suspend*/ fun getAllUsers(): List<UserData>
}
