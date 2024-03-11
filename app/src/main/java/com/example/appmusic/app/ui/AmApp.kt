package com.example.appmusic.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appmusic.feature.music.ArtistScreen
import com.example.appmusic.feature.music.ArtistViewModel
import com.example.appmusic.feature.song.FavoriteSongScreen
import com.example.appmusic.feature.song.FavoriteSongViewModel
import com.example.appmusic.feature.song.SongByArtistViewModel
import com.example.appmusic.feature.song.SongScreen

@Composable
fun AmApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "artistRoute"
        ) {
            composable("artistRoute") {
                val viewModel: ArtistViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ArtistScreen(
                    uiState = uiState,
                    navToSong = { id -> navController.navigate("songRoute/$id") },
                    navToFavorite = { navController.navigate("favoriteRoute") })
            }
            composable("songRoute/{artistId}",
                arguments = listOf(
                    navArgument("artistId") { type = NavType.IntType }
                )
            ) {
                val viewModel: SongByArtistViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                SongScreen(uiState = uiState)
            }
            composable("favoriteRoute") {
                val viewModel: FavoriteSongViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                FavoriteSongScreen(uiState = uiState)
            }
        }
    }
}
