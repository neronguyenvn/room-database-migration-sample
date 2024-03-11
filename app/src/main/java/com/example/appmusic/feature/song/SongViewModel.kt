package com.example.appmusic.feature.song

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.database.model.ArtistWithSongs
import com.example.appmusic.core.database.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SongUiState(
    val artistWithSongs: ArtistWithSongs? = null,
    val addingSong: Song? = null,
    val eventSink: (SongEvent) -> Unit = {}
)

@HiltViewModel
class SongViewModel @Inject constructor(
    private val songRepository: SongRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val artistId = savedStateHandle.get<Int>("artistId")!!
    private val _addingSong = MutableStateFlow(
        Song(
            name = "",
            image = "",
            artistId = artistId
        )
    )

    private val eventSink: (SongEvent) -> Unit = { event ->
        when (event) {
            SongEvent.AddSong -> viewModelScope.launch {
                songRepository.addSong(_addingSong.value)
            }

            is SongEvent.InputImage -> _addingSong.update { it.copy(image = event.value) }

            is SongEvent.InputName -> _addingSong.update { it.copy(name = event.value) }
        }
    }

    val uiState = combine(
        songRepository.getSongs(artistId),
        _addingSong
    ) { list, song ->

        SongUiState(
            artistWithSongs = list,
            addingSong = song,
            eventSink = eventSink
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = SongUiState(),
        started = SharingStarted.WhileSubscribed(5000)
    )
}