package com.example.submission1intermediate.background.response

data class LoginResponse(
    val loginResult:LoginResult,
    val error: Boolean,
    val message: String
)
