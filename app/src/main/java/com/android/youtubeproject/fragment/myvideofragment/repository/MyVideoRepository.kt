package com.android.youtubeproject.fragment.myvideofragment.repository

import com.android.youtubeproject.fragment.myvideofragment.db.UserDao
import com.android.youtubeproject.fragment.myvideofragment.db.UserData

class MyVideoRepository(private val userDao: UserDao) {

    suspend fun getUserByIndex(index: Int): UserData? {
        return userDao.getUserByIndex(index)
    }

    suspend fun insertUser(user: UserData) {
        userDao.insert(user)
    }

    suspend fun deleteUser(user: UserData) {
        userDao.delete(user)
    }

    suspend fun getAllUsers(): List<UserData> {
        return userDao.getAllUsers()
    }
}
