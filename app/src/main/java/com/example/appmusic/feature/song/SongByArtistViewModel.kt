package com.example.appmusic.feature.song

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusic.core.data.ArtistRepository
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.database.model.ArtistWithSongs
import com.example.appmusic.core.database.model.Song
import com.example.appmusic.feature.song.SongByArtistEvent.AddSongByArtist
import com.example.appmusic.feature.song.SongByArtistEvent.ClickFavorite
import com.example.appmusic.feature.song.SongByArtistEvent.InputImage
import com.example.appmusic.feature.song.SongByArtistEvent.InputName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SongsByArtistUiState(
    val artistWithSongs: ArtistWithSongs? = null,
    val addingSong: Song? = null,
    val eventSink: (SongByArtistEvent) -> Unit = {}
)

@HiltViewModel
class SongByArtistViewModel @Inject constructor(
    private val songRepository: SongRepository,
    artistRepository: ArtistRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val artistId = savedStateHandle.get<Int>("artistId")!!
    private val _addingSong = MutableStateFlow(
        Song(
            name = "",
            image = "",
            isFavorite = false,
            artistId = artistId
        )
    )

    private val eventSink: (SongByArtistEvent) -> Unit = { event ->
        when (event) {
            AddSongByArtist -> viewModelScope.launch {
                songRepository.addSong(_addingSong.value)
                _addingSong.update {
                    it.copy(
                        name = "",
                        image = "",
                        isFavorite = false
                    )
                }
            }

            is InputImage -> _addingSong.update { it.copy(image = event.value) }

            is InputName -> _addingSong.update { it.copy(name = event.value) }

            is ClickFavorite -> viewModelScope.launch {
                songRepository.setFavorite(event.songId)
            }
        }
    }

    val uiState = combine(
        artistRepository.getArtistWithSongsStream(artistId),
        _addingSong,
    ) { list, song ->

        SongsByArtistUiState(
            artistWithSongs = list,
            addingSong = song,
            eventSink = eventSink
        )

    }.stateIn(
        scope = viewModelScope,
        initialValue = SongsByArtistUiState(),
        started = SharingStarted.WhileSubscribed(5000)
    )
}