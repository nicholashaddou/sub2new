package com.example.submission1intermediate.background.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class ListStory(
    @PrimaryKey
    val id:String,
    val name: String,
    val description:String,
    val photoUrl:String,
    val createdAt:String,
    val lat:Double,
    val lon:Double
):Parcelable

