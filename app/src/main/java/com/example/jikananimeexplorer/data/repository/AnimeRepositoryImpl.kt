package com.example.jikananimeexplorer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.remote.AnimeApi
import com.example.jikananimeexplorer.data.remote.AnimeRemoteDataSource
import com.example.jikananimeexplorer.data.remote.mediator.AnimeRemoteMediator
import com.example.jikananimeexplorer.domain.model.Anime
import com.example.jikananimeexplorer.domain.repository.AnimeRepository
import com.example.jikananimeexplorer.utils.AnimeMapper
import com.example.jikananimeexplorer.utils.result.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
    private val database: AnimeDatabase,
    private val remoteDataSource: AnimeRemoteDataSource
) : AnimeRepository {
    val animeDao = database.animeDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopAnimePaging(): Flow<PagingData<Anime>> {
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
        when (val result = remoteDataSource.getAnimeDetail(animeId)) {
            is Resource.Success -> {
                val dto = result.data.data
                val entity = AnimeMapper.dtoToEntity(dto)
                animeDao.insertAnimeList(listOf(entity))
                return AnimeMapper.entityToDomain(entity)
            }

            is Resource.Error -> {
                // fallback to DB
                val cached = database.animeDao().getAnimeById(animeId)
                return cached?.let { AnimeMapper.entityToDomain(it) }
            }

            is Resource.Loading -> return null
        }
    }

}

