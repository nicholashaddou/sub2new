package com.example.submission1intermediate.data.API

import com.example.submission1intermediate.data.response.FileUploadResponse
import com.example.submission1intermediate.data.response.LoginResponse
import com.example.submission1intermediate.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<FileUploadResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String
    ) : Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<StoryResponse>
}