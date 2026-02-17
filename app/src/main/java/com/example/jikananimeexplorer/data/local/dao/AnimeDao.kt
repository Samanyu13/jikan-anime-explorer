package com.example.jikananimeexplorer.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikananimeexplorer.data.local.entities.AnimeEntity


@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY id ASC")
    fun getAnimePagingSource(): PagingSource<Int, AnimeEntity>

    @Query("SELECT * FROM anime WHERE id = :animeId")
    suspend fun getAnimeById(animeId: Int): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(anime: List<AnimeEntity>)


    @Query("DELETE FROM anime")
    suspend fun clearAll()
}