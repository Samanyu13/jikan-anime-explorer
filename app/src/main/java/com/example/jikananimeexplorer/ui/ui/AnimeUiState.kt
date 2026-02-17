package com.example.jikananimeexplorer.ui.ui

import com.example.jikananimeexplorer.domain.model.Anime

sealed class AnimeUiState {

    object Loading : AnimeUiState()

    data class Success(
        val animeList: List<Anime>
    ) : AnimeUiState()

    data class Error(
        val message: String
    ) : AnimeUiState()
}
