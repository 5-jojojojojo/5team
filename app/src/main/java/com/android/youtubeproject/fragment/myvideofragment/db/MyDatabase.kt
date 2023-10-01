package com.android.youtubeproject.fragment.myvideofragment.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.youtubeproject.App

@Database(entities = [UserData::class], exportSchema = false, version = 1)
@TypeConverters(UriConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getUser() : UserDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getDatabase() : MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    App.instance.applicationContext, MyDatabase::class.java, "my-youtube-db-test")
                    .build()
            }
            return INSTANCE as MyDatabase
        }
    }
}