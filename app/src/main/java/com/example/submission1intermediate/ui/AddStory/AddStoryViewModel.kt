package com.example.submission1intermediate.ui.AddStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission1intermediate.background.UserModel
import com.example.submission1intermediate.background.preference.UserPreference

class AddStoryViewModel(private val pref: UserPreference) : ViewModel(){
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}