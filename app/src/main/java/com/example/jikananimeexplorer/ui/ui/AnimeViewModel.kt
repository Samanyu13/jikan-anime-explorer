package com.example.jikananimeexplorer.ui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jikananimeexplorer.domain.repository.AnimeRepository
import com.example.jikananimeexplorer.domain.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    /* ------------------------------------------------ */
    /* Paging List                                      */
    /* ------------------------------------------------ */

    /*
       Paging flow exposed to UI
       cachedIn() prevents refetch during config change
     */
    val animePagingFlow: Flow<PagingData<Anime>> =
        repository
            .getTopAnimePaging()
            .cachedIn(viewModelScope)


    /* ------------------------------------------------ */
    /* Detail Screen State                              */
    /* ------------------------------------------------ */

    private val _animeDetailState =
        MutableStateFlow<AnimeDetailUiState>(AnimeDetailUiState.Idle)

    val animeDetailState: StateFlow<AnimeDetailUiState> =
        _animeDetailState


    /* ------------------------------------------------ */
    /* Fetch Anime Detail                               */
    /* ------------------------------------------------ */

    fun fetchAnimeDetail(animeId: Int) {

        viewModelScope.launch {

            _animeDetailState.value = AnimeDetailUiState.Loading

            try {

                val anime = repository.getAnimeDetail(animeId)

                if (anime != null) {
                    _animeDetailState.value =
                        AnimeDetailUiState.Success(anime)
                } else {
                    _animeDetailState.value =
                        AnimeDetailUiState.Error("Anime not found")
                }

            } catch (e: Exception) {

                _animeDetailState.value =
                    AnimeDetailUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }


    /* ------------------------------------------------ */
    /* Reset Detail State (useful when leaving screen)  */
    /* ------------------------------------------------ */

    fun clearDetailState() {
        _animeDetailState.value = AnimeDetailUiState.Idle
    }
}
