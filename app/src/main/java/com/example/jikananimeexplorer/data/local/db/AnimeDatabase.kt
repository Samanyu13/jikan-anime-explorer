package com.example.jikananimeexplorer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jikananimeexplorer.data.local.dao.AnimeDao
import com.example.jikananimeexplorer.data.local.dao.RemoteKeysDao
import com.example.jikananimeexplorer.data.local.entities.AnimeEntity

@Database(
    entities = [
        AnimeEntity::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}