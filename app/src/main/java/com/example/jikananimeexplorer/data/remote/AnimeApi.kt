package com.example.jikananimeexplorer.data.remote

import com.example.jikananimeexplorer.data.remote.dto.AnimeDetailResponse
import com.example.jikananimeexplorer.data.remote.dto.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    // Top Anime with Paging
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int
    ): AnimeResponse


    // Anime Details
    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ): AnimeDetailResponse
}
