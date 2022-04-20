package com.example.submission1intermediate.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.submission1intermediate.data.API.ApiConfig
import com.example.submission1intermediate.data.UserModel
import com.example.submission1intermediate.data.preference.UserPreference
import com.example.submission1intermediate.data.response.ListStory
import com.example.submission1intermediate.data.response.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String> = _apiResponse

    val listUser = MutableLiveData<ArrayList<ListStory>>()


    fun getListStory(token: String){
        val storyList = ArrayList<ListStory>()
        val client = ApiConfig.getApiService().getStories("Bearer $token")

        _isLoading.value = true
        client.enqueue(object: Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    listUser.postValue(responseBody.listStory)
                    listUser.value = storyList
                }else{
                    _isLoading.value = false
                    _apiResponse.value = responseBody?.message
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                _apiResponse.value = t.message
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}