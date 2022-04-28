package com.example.submission1intermediate.background.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission1intermediate.background.API.KeysData

@Dao
interface KeysDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<KeysData>)


    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): KeysData?


    @Query("DELETE FROM keys")
    suspend fun deleteKeys()

}