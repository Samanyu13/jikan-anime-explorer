package com.example.jikananimeexplorer.data.remote

import com.example.jikananimeexplorer.data.remote.dto.AnimeDetailResponse
import com.example.jikananimeexplorer.data.remote.dto.AnimeResponse
import com.example.jikananimeexplorer.utils.result.Resource
import com.example.jikananimeexplorer.utils.result.safeApiCall
import javax.inject.Inject

class AnimeRemoteDataSource @Inject constructor(
    private val api: AnimeApi
) {
    suspend fun getTopAnime(page: Int): Resource<AnimeResponse> {
        return safeApiCall {
            api.getTopAnime(page)
        }
    }

    suspend fun getAnimeDetail(id: Int): Resource<AnimeDetailResponse> {
        return safeApiCall {
            api.getAnimeDetails(id)
        }
    }
}
