package com.example.jikananimeexplorer.data.remote.dto

data class AnimeResponse(
    val data: List<AnimeDto>,
    val pagination: PaginationDto
)

data class AnimeDetailResponse(
    val data: AnimeDto
)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val images: ImagesDto,
    val trailer: TrailerDto?,
    val genres: List<GenreDto>
)

data class ImagesDto(
    val jpg: JpgDto
)

data class JpgDto(
    val image_url: String?
)

data class TrailerDto(
    val url: String?
)

data class GenreDto(
    val name: String
)

data class PaginationDto(
    val last_visible_page: Int,
    val has_next_page: Boolean
)