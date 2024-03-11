package com.example.appmusic.feature.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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

sealed interface SongEvent {

    data object AddSong : SongEvent

    data class InputName(val value: String) : SongEvent

    data class InputImage(val value: String) : SongEvent
}

@Composable
fun SongScreen(uiState: SongUiState) {

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        uiState.artistWithSongs?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = ("Songs of " + it.artist.name),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(it.songs) { song ->
                    SongItem(song)
                }
            }
        }
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
                        uiState.eventSink(SongEvent.InputName(it))
                    }
                    AddTextFieldRow(
                        icon = Icons.Default.BookOnline,
                        label = "Image",
                        value = uiState.addingSong?.image ?: ""
                    ) {
                        uiState.eventSink(SongEvent.InputImage(it))
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(onClick = { showAddDialog = false }) {
                            Text("CANCEL")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            uiState.eventSink(SongEvent.AddSong)
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

@Composable
fun SongItem(song: Song) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = song.name, style = MaterialTheme.typography.titleMedium)
            InfoRow(icon = Icons.Default.BookOnline, infoName = "Image", infoContent = song.image)
        }
    }
}