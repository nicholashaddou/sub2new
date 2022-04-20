package com.example.submission1intermediate.data.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)
