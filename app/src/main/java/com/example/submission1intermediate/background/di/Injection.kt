package com.example.submission1intermediate.background.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.submission1intermediate.background.API.ApiConfig
import com.example.submission1intermediate.background.data.StoryRepository
import com.example.submission1intermediate.background.database.StoryDatabase

@ExperimentalPagingApi
object Injection {
    fun provideRepo(context: Context?): StoryRepository {
        val database = context?.let { StoryDatabase.getDatabase(it) }
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}