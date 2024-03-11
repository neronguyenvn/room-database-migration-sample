package com.example.appmusic.feature.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.appmusic.core.database.model.Song
import com.example.appmusic.core.designsystem.component.AddTextFieldRow
import com.example.appmusic.core.designsystem.component.InfoRow
import com.example.appmusic.feature.song.SongByArtistEvent.ClickFavorite

sealed interface SongByArtistEvent {

    data object AddSongByArtist : SongByArtistEvent

    data class InputName(val value: String) : SongByArtistEvent

    data class InputImage(val value: String) : SongByArtistEvent

    data class ClickFavorite(val songId: Int) : SongByArtistEvent
}

@Composable
fun SongScreen(uiState: SongsByArtistUiState) {

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        SongsLazyColumn(
            title = "Songs by ${uiState.artistWithSongs?.artist?.name}",
            songs = uiState.artistWithSongs?.songs ?: emptyList(),
            modifier = Modifier.padding(paddingValues)
        ) { id ->
            uiState.eventSink(ClickFavorite(id))
        }

        if (showAddDialog) {
            Dialog(onDismissRequest = { showAddDialog = false }) {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Add Song", style = MaterialTheme.typography.titleMedium)
                        AddTextFieldRow(
                            icon = Icons.Default.Book,
                            label = "Name",
                            value = uiState.addingSong?.name ?: ""
                        ) {
                            uiState.eventSink(SongByArtistEvent.InputName(it))
                        }
                        AddTextFieldRow(
                            icon = Icons.Default.BookOnline,
                            label = "Image",
                            value = uiState.addingSong?.image ?: ""
                        ) {
                            uiState.eventSink(SongByArtistEvent.InputImage(it))
                        }
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            OutlinedButton(onClick = { showAddDialog = false }) {
                                Text("CANCEL")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                uiState.eventSink(SongByArtistEvent.AddSongByArtist)
                                showAddDialog = false
                            }) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SongsLazyColumn(
    title: String,
    songs: List<Song>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
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
            SongItem(song) { id ->
                onFavoriteClick(id)
            }
        }
    }
}


@Composable
fun SongItem(song: Song, onFavoriteClick: (Int) -> Unit) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = song.name, style = MaterialTheme.typography.titleMedium)
                InfoRow(
                    icon = Icons.Default.BookOnline,
                    infoName = "Image",
                    infoContent = song.image
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onFavoriteClick(song.id) }) {
                Icon(
                    imageVector = if (song.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null
                )
            }
        }
    }
}


