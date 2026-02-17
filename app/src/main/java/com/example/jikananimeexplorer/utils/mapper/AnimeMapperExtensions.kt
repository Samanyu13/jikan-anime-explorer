package com.example.jikananimeexplorer.utils.mapper

import com.example.jikananimeexplorer.data.local.entities.AnimeEntity
import com.example.jikananimeexplorer.data.remote.dto.AnimeDto
import com.example.jikananimeexplorer.domain.model.Anime
import com.example.jikananimeexplorer.utils.AnimeMapper


fun AnimeDto.toEntity(): AnimeEntity =
    AnimeMapper.dtoToEntity(this)

fun AnimeEntity.toDomain(): Anime =
    AnimeMapper.entityToDomain(this)