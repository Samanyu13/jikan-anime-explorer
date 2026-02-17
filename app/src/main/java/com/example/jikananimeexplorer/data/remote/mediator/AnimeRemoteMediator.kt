package com.example.jikananimeexplorer.data.remote.mediator


import androidx.paging.*
import androidx.room.withTransaction
import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.local.db.RemoteKeys
import com.example.jikananimeexplorer.data.local.entities.AnimeEntity
import com.example.jikananimeexplorer.data.remote.AnimeApi
import com.example.jikananimeexplorer.utils.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val animeApi: AnimeApi, private val database: AnimeDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    private val animeDao = database.animeDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, AnimeEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextKey
                }
            }

            // 🌐 Network Call
            val response = animeApi.getTopAnime(page)

            val animeList = response.data
            val endOfPaginationReached = !response.pagination.has_next_page

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    animeDao.clearAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = animeList.map {
                    RemoteKeys(
                        animeId = it.mal_id, prevKey = prevKey, nextKey = nextKey
                    )
                }

                val entities = animeList.map { it.toEntity() }

                remoteKeysDao.insertAll(keys)
                animeDao.insertAnimeList(entities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    // ---------- Helper Functions ----------

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, AnimeEntity>
    ): RemoteKeys? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { anime ->
                remoteKeysDao.getRemoteKeys(anime.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, AnimeEntity>
    ): RemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { anime ->
                remoteKeysDao.getRemoteKeys(anime.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, AnimeEntity>
    ): RemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }
}
