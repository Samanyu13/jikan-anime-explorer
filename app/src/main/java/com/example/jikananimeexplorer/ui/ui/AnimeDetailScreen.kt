package com.example.jikananimeexplorer.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jikananimeexplorer.domain.model.Anime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    viewModel: AnimeViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.animeDetailState.collectAsState()
    LaunchedEffect(animeId) {
        viewModel.fetchAnimeDetail(animeId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (uiState) {
                        is AnimeDetailUiState.Success ->
                            (uiState as AnimeDetailUiState.Success).anime.title

                        else -> "Anime Detail"
                    }
                    Text(title)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (uiState) {
                // LOADING
                is AnimeDetailUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // SUCCESS
                is AnimeDetailUiState.Success -> {
                    val anime =
                        (uiState as AnimeDetailUiState.Success).anime
                    AnimeDetailContent(anime)
                }

                // ERROR
                is AnimeDetailUiState.Error -> {
                    Text(
                        text = (uiState as AnimeDetailUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun AnimeDetailContent(anime: Anime) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Poster
        AsyncImage(
            model = anime.posterUrl,
            contentDescription = anime.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = anime.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Score
        anime.score?.let {
            Text("⭐ Score: $it")
        }

        // Episodes
        anime.episodes?.let {
            Text("Episodes: $it")
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Genres
        if (anime.genres.isNotEmpty()) {
            Text(
                text = "Genres",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                anime.genres.forEach { genre ->
                    AssistChip(
                        onClick = {},
                        label = { Text(genre) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Synopsis
        anime.synopsis?.let {
            Text(
                text = "Synopsis",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(it)
        }
    }
}
