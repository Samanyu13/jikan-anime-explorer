package com.example.jikananimeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikananimeexplorer.data.local.db.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeys>)


    @Query("SELECT * FROM remote_keys WHERE animeId = :id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys?


    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}