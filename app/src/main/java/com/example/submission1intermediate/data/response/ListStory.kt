package com.example.submission1intermediate.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListStory(
    val id:String,
    val name: String,
    val description:String,
    val photoUrl:String,
    val createdAt:String,
    val lat:Double,
    val lon:Double
):Parcelable
