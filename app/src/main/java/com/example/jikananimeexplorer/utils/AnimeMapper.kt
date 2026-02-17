package com.example.jikananimeexplorer.utils

import com.example.jikananimeexplorer.data.local.entities.AnimeEntity
import com.example.jikananimeexplorer.data.remote.dto.AnimeDto
import com.example.jikananimeexplorer.domain.model.Anime

object AnimeMapper {
    // DTO → Entity
    fun dtoToEntity(dto: AnimeDto): AnimeEntity {

        val genresString =
            dto.genres.joinToString(",") { it.name }

        return AnimeEntity(
            id = dto.mal_id,
            title = dto.title,
            episodes = dto.episodes,
            score = dto.score,
            synopsis = dto.synopsis,
            posterUrl = dto.images.jpg.image_url,
            trailerUrl = dto.trailer?.url,
            genres = genresString
        )
    }

    // Entity → Domain
    fun entityToDomain(entity: AnimeEntity): Anime {

        val genresList =
            if (entity.genres.isEmpty())
                emptyList()
            else
                entity.genres.split(",")

        return Anime(
            id = entity.id,
            title = entity.title,
            episodes = entity.episodes,
            score = entity.score,
            synopsis = entity.synopsis,
            posterUrl = entity.posterUrl,
            trailerUrl = entity.trailerUrl,
            genres = genresList
        )
    }
}
