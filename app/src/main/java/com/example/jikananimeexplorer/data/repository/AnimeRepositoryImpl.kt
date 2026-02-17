package com.example.jikananimeexplorer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.remote.AnimeApi
import com.example.jikananimeexplorer.data.remote.mediator.AnimeRemoteMediator
import com.example.jikananimeexplorer.domain.model.Anime
import com.example.jikananimeexplorer.domain.repository.AnimeRepository
import com.example.jikananimeexplorer.utils.AnimeMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
    private val database: AnimeDatabase
) : AnimeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopAnimePaging(): Flow<PagingData<Anime>> {

        val animeDao = database.animeDao()

        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),

            remoteMediator = AnimeRemoteMediator(
                animeApi = animeApi,
                database = database
            ),

            pagingSourceFactory = {
                animeDao.getAnimePagingSource()
            }

        ).flow.map { pagingData ->

            pagingData.map { entity ->
                AnimeMapper.entityToDomain(entity)
            }

        }
    }

    override suspend fun getAnimeDetail(animeId: Int): Anime? {

        val animeDao = database.animeDao()

        // ✅ Offline First
        val localEntity = animeDao.getAnimeById(animeId)
        if (localEntity != null) {
            return AnimeMapper.entityToDomain(localEntity)
        }

        return try {

            val response = animeApi.getAnimeDetails(animeId)
            val dto = response.data

            val entity = AnimeMapper.dtoToEntity(dto)

            animeDao.insertAnimeList(listOf(entity))

            AnimeMapper.entityToDomain(entity)

        } catch (e: Exception) {
            null
        }
    }
}

