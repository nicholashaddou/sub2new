package com.example.submission1intermediate.background.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1intermediate.background.API.KeysData
import com.example.submission1intermediate.background.data.StoryDAO
import com.example.submission1intermediate.background.response.ListStory

@Database(entities = [ListStory::class, KeysData::class], version = 1)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDAO
    abstract fun keysDao(): KeysDAO

    companion object {

        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StoryDatabase::class.java, "story_database")
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
//        fun getDatabase(context: Context): StoryDatabase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    StoryDatabase::class.java, "story database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
//            }
//        }

    }
