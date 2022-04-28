package com.example.submission1intermediate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.submission1intermediate.background.UserModel
import com.example.submission1intermediate.background.data.StoryRepository
import com.example.submission1intermediate.background.preference.UserPreference
import com.example.submission1intermediate.background.response.ListStory
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class GeneralViewModel(private val pref: UserPreference, private val storyRepository: StoryRepository) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun getData(): List<ListStory>{
        return storyRepository.getData()
    }

    fun login(user:UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }
}