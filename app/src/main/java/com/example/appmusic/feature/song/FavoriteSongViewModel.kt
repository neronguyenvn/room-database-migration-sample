package com.example.appmusic.feature.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.database.model.SongWithArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoriteSongUiState(
    val favoriteSongs: List<SongWithArtist> = emptyList(),
    val eventSink: (FavoriteSongEvent) -> Unit = {}
)

@HiltViewModel
class FavoriteSongViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {
    private val eventSink: (FavoriteSongEvent) -> Unit = { event ->
        when (event) {
            is FavoriteSongEvent.ClickFavorite -> viewModelScope.launch {
                songRepository.setFavorite(event.songId)
            }
        }
    }

    val uiState = songRepository.getFavoriteSongsStream().map {
        FavoriteSongUiState(
            favoriteSongs = it,
            eventSink = eventSink
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = FavoriteSongUiState(),
        started = SharingStarted.WhileSubscribed(5000)
    )
}