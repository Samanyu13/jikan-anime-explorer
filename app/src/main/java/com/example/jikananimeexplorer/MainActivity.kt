package com.example.jikananimeexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jikananimeexplorer.ui.ui.AnimeDetailScreen
import com.example.jikananimeexplorer.ui.ui.AnimeListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeApp()
        }
    }
}

@Composable
fun AnimeApp() {

    val navController = rememberNavController()

    MaterialTheme {

        NavHost(
            navController = navController,
            startDestination = "anime_list"
        ) {

            composable("anime_list") {
                AnimeListScreen(
                    onAnimeClick = { animeId ->
                        navController.navigate("anime_detail/$animeId")
                    }
                )
            }

            composable("anime_detail/{animeId}") { backStackEntry ->
                val animeId =
                    backStackEntry.arguments
                        ?.getString("animeId")
                        ?.toIntOrNull() ?: return@composable

                AnimeDetailScreen(animeId = animeId, onBackClick = {
                    navController.navigate("anime_list")
                })
            }
        }
    }
}