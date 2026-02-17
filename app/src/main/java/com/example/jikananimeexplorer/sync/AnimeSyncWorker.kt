package com.example.jikananimeexplorer.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.jikananimeexplorer.data.local.db.AnimeDatabase
import com.example.jikananimeexplorer.data.local.entities.AnimeEntity
import com.example.jikananimeexplorer.data.remote.AnimeApi
import com.example.jikananimeexplorer.utils.AnimeMapper
import com.example.jikananimeexplorer.utils.Constants.MAX_CACHE_ITEM_COUNT
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class AnimeSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val animeApi: AnimeApi,
    private val database: AnimeDatabase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            database.withTransaction {
                val animeDao = database.animeDao()
                val allAnime = mutableListOf<AnimeEntity>()
                var page = 1

                while (allAnime.size < 100) {
                    if (isStopped) return@withTransaction Result.failure()
                    val response = animeApi.getTopAnime(page)
                    val mapped = response.data.map {
                        AnimeMapper.dtoToEntity(it)
                    }
                    allAnime.addAll(mapped)
                    page++
                    delay(300)
                }

                // Keep only first MAX_CACHE_ITEM_COUNT
                val top100 = allAnime.take(MAX_CACHE_ITEM_COUNT)

                animeDao.clearAll()
                animeDao.insertAnimeList(top100)
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("AnimeSyncWorker", "Exception: ${e.stackTrace}")
            Result.retry()
        }
    }
}

