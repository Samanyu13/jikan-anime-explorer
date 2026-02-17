package com.example.jikananimeexplorer.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val posterUrl: String?,
    val trailerUrl: String?,
    val genres: List<String>
)