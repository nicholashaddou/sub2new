package com.example.submission1intermediate.background.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.submission1intermediate.background.API.ApiService
import com.example.submission1intermediate.background.database.StoryDatabase
import com.example.submission1intermediate.background.response.ListStory

@ExperimentalPagingApi
class StoryRepository(private val storyDatabase: StoryDatabase?, private val apiService: ApiService) {

    fun find(token: String): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = storyDatabase?.let { RemoteMediator(it, apiService, token) },
            pagingSourceFactory = {
                storyDatabase?.storyDao()!!.getAllStory()
            }
        ).liveData
    }

    suspend fun getData(): List<ListStory> {
        return storyDatabase?.storyDao()!!.findAll()
    }
}