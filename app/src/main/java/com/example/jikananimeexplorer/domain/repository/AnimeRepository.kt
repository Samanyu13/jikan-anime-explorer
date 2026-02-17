package com.example.jikananimeexplorer.domain.repository

import androidx.paging.PagingData
import com.example.jikananimeexplorer.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getTopAnimePaging(): Flow<PagingData<Anime>>

    suspend fun getAnimeDetail(animeId: Int): Anime?
}