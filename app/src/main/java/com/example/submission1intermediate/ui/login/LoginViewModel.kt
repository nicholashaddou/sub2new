package com.example.submission1intermediate.ui.login

import android.content.Context
import androidx.lifecycle.*
import com.example.submission1intermediate.R
import com.example.submission1intermediate.background.API.ApiConfig
import com.example.submission1intermediate.background.UserModel
import com.example.submission1intermediate.background.preference.UserPreference
import com.example.submission1intermediate.background.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String> = _apiResponse

    private val _isEmailEmpty = MutableLiveData<Boolean>()
    val isEmailEmpty: LiveData<Boolean> = _isEmailEmpty

    private val _isPasswordEmpty = MutableLiveData<Boolean>()
    val isPasswordEmpty: LiveData<Boolean> = _isPasswordEmpty

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(user:UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }

    private fun emailValidation(email: String) : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun passwordValidation(pass: String) : Boolean{
        return pass.length >= 6
    }

    private fun formNotEmpty(email: String, password: String): Boolean{
        var isNotEmpty = true
        if(email.isEmpty()){
            _isEmailEmpty.value = true
            isNotEmpty = false
        }
        if(password.isEmpty()){
            _isPasswordEmpty.value = true
            isNotEmpty = false
        }
        return isNotEmpty
    }
    private fun formValidation(email: String,pass: String):Boolean{
        var isValid = true
        if(formNotEmpty(email,pass)){
            if(!emailValidation(email)){
                _isEmailValid.value = false
                isValid = false
            }else if(!passwordValidation(pass)){
                _isPasswordValid.value = false
                isValid = false
            }
        }else{ isValid = false}
        return isValid
    }

    fun loginUser(context: Context, email: String, password: String){
        if(formValidation(email,password)) {
            val client = ApiConfig.getApiService().postLogin(email, password)
            _isLoading.value = true
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        val error = responseBody.error
                        if (!error) {
                            val user = UserModel(
                                responseBody.loginResult.userId,
                                responseBody.loginResult.name,
                                responseBody.loginResult.token,
                                true
                            )
                            login(user)
                            _isLoading.value = false
                            _apiResponse.value = responseBody.message
                        } else {
                            _isLoading.value = false
                            _apiResponse.value = context.getString(R.string.login_failed)
                        }
                    } else {
                        _isLoading.value = false
                        _apiResponse.value = context.getString(R.string.wrong_credentials)
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    _apiResponse.value = t.message
                }

            })
        }
    }
}