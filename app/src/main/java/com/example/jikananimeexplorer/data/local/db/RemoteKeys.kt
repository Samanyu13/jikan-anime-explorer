package com.example.jikananimeexplorer.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(

    @PrimaryKey
    val animeId: Int,

    val prevKey: Int?,

    val nextKey: Int?
)