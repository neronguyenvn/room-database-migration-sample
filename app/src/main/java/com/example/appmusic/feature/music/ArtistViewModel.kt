package com.example.appmusic.feature.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusic.core.data.ArtistRepository
import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.feature.music.ArtistEvent.AddArtist
import com.example.appmusic.feature.music.ArtistEvent.InputAge
import com.example.appmusic.feature.music.ArtistEvent.InputImage
import com.example.appmusic.feature.music.ArtistEvent.InputName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArtistUiState(
    val artists: List<Artist> = emptyList(),
    val addingArtist: Artist = Artist(),
    val eventSink: (ArtistEvent) -> Unit = {}
)

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository
) : ViewModel() {

    private val _addingArtist = MutableStateFlow(Artist())

    private val eventSink: (ArtistEvent) -> Unit = { event ->
        when (event) {
            is AddArtist -> viewModelScope.launch {
                artistRepository.addArtist(_addingArtist.value)
                _addingArtist.value = Artist()
            }

            is InputAge -> _addingArtist.update { it.copy(age = event.value.toInt()) }

            is InputImage -> _addingArtist.update { it.copy(image = event.value) }

            is InputName -> _addingArtist.update { it.copy(name = event.value) }
        }
    }

    val uiState = combine(
        artistRepository.getArtistsStream(),
        _addingArtist,
    ) { list, artist ->
        ArtistUiState(
            artists = list,
            addingArtist = artist,
            eventSink = eventSink
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = ArtistUiState(),
        started = SharingStarted.WhileSubscribed(5000)
    )
}
