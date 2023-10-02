package com.android.youtubeproject.fragment.myvideofragment.db

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userData: UserData)

    @Delete
    suspend fun delete(userData: UserData)

    @Query("SELECT * FROM user_table WHERE `index` = :index")
    suspend fun getUserByIndex(index: Int): UserData?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserData>
}
