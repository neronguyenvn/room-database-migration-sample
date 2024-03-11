package com.example.appmusic.feature.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appmusic.core.database.model.SongWithArtist
import com.example.appmusic.core.designsystem.component.InfoRow

sealed interface FavoriteSongEvent {

    data class ClickFavorite(val songId: Int) : FavoriteSongEvent
}

@Composable
fun FavoriteSongScreen(uiState: FavoriteSongUiState) {
    FavoriteSongLazyColumn(title = "Favorite Song", songs = uiState.favoriteSongs) {
        uiState.eventSink(FavoriteSongEvent.ClickFavorite(it))
    }
}

@Composable
fun FavoriteSongLazyColumn(
    title: String,
    songs: List<SongWithArtist>,
    onFavoriteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(songs) { song ->
            FavoriteSongItem(song) { id ->
                onFavoriteClick(id)
            }
        }
    }
}

@Composable
fun FavoriteSongItem(song: SongWithArtist, onFavoriteClick: (Int) -> Unit) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = song.song.name, style = MaterialTheme.typography.titleMedium)
                InfoRow(
                    icon = Icons.Default.BookOnline,
                    infoName = "Image",
                    infoContent = song.song.image
                )
                InfoRow(
                    icon = Icons.Default.Person,
                    infoName = "Artist Name",
                    infoContent = song.artist.name
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onFavoriteClick(song.song.id) }) {
                Icon(
                    imageVector = if (song.song.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null
                )
            }
        }
    }
}

