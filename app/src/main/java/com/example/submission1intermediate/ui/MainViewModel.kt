package com.example.submission1intermediate.ui

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission1intermediate.background.API.ApiConfig
import com.example.submission1intermediate.background.UserModel
import com.example.submission1intermediate.background.data.StoryRepository
import com.example.submission1intermediate.background.preference.UserPreference
import com.example.submission1intermediate.background.response.ListStory
import com.example.submission1intermediate.background.response.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalPagingApi
class MainViewModel(private val pref: UserPreference, private val storyRepository: StoryRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String> = _apiResponse

    val listUser = MutableLiveData<ArrayList<ListStory>>()


//    fun getListStory(token: String){
//        val storyList = ArrayList<ListStory>()
//        val client = ApiConfig.getApiService().getStories("Bearer $token",2,2)//temp values
//
//        _isLoading.value = true
//        client.enqueue(object: Callback<StoryResponse> {
//            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
//                val responseBody = response.body()
//                if(response.isSuccessful && responseBody != null){
//                    listUser.postValue(responseBody.listStory)
//                    listUser.value = storyList
//                }else{
//                    _isLoading.value = false
//                    _apiResponse.value = responseBody?.message
//                }
//            }
//
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                _isLoading.value = false
//                _apiResponse.value = t.message
//            }
//        })
//    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    suspend fun getData(): List<ListStory>{
        return storyRepository.getData()
    }

    val pagingStory: (String) -> LiveData<PagingData<ListStory>> = { token: String ->
        storyRepository.find("Bearer $token").cachedIn(viewModelScope)
    }

}