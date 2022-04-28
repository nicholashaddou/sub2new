package com.example.submission1intermediate

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.submission1intermediate.background.di.Injection
import com.example.submission1intermediate.background.preference.UserPreference
import com.example.submission1intermediate.ui.AddStory.AddStoryViewModel
import com.example.submission1intermediate.ui.GeneralViewModel
import com.example.submission1intermediate.ui.MainViewModel
import com.example.submission1intermediate.ui.login.LoginViewModel

@ExperimentalPagingApi
class ViewModelFactory(private val pref: UserPreference, private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref, Injection.provideRepo(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(pref) as T
            }
            modelClass.isAssignableFrom(GeneralViewModel::class.java) -> {
                GeneralViewModel(pref, Injection.provideRepo(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}