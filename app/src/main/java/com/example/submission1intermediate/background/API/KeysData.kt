package com.example.submission1intermediate.background.API

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class KeysData(
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
