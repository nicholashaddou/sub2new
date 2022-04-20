package com.example.submission1intermediate.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val loginResult:LoginResult,
    val error: Boolean,
    val message: String
)
