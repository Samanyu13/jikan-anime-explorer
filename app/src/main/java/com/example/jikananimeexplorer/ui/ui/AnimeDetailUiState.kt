package com.example.jikananimeexplorer.ui.ui

import com.example.jikananimeexplorer.domain.model.Anime

sealed class AnimeDetailUiState {
    object Idle : AnimeDetailUiState()
    object Loading : AnimeDetailUiState()
    data class Success(
        val anime: Anime
    ) : AnimeDetailUiState()
    data class Error(
        val message: String
    ) : AnimeDetailUiState()
}
