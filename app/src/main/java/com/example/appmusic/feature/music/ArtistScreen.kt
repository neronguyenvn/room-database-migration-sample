package com.example.appmusic.feature.music

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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.core.designsystem.component.AddTextFieldRow
import com.example.appmusic.core.designsystem.component.InfoRow
import com.example.appmusic.feature.music.ArtistEvent.AddArtist
import com.example.appmusic.feature.music.ArtistEvent.InputAge
import com.example.appmusic.feature.music.ArtistEvent.InputImage
import com.example.appmusic.feature.music.ArtistEvent.InputName

sealed interface ArtistEvent {

    data object AddArtist : ArtistEvent

    data class InputName(val value: String) : ArtistEvent

    data class InputImage(val value: String) : ArtistEvent

    data class InputAge(val value: String) : ArtistEvent
}

@Composable
fun ArtistScreen(
    uiState: ArtistUiState,
    navToSong: (Int) -> Unit,
    navToFavorite: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = ("Artist List"),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { navToFavorite() }) {
                        Text(text = "View favorite songs")
                    }
                }
            }
            items(uiState.artists) { artist ->
                ArtistItem(artist) {
                    navToSong(artist.id)
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
                    Text(text = "Add Artist", style = MaterialTheme.typography.titleMedium)
                    AddTextFieldRow(
                        icon = Icons.Default.Person,
                        label = "Name",
                        value = uiState.addingArtist.name
                    ) {
                        uiState.eventSink(InputName(it))
                    }
                    AddTextFieldRow(
                        icon = Icons.Default.AccountBox,
                        label = "Image",
                        value = uiState.addingArtist.image
                    ) {
                        uiState.eventSink(InputImage(it))
                    }
                    AddTextFieldRow(
                        icon = Icons.Default.DateRange,
                        label = "Age",
                        value = uiState.addingArtist.age.let { if (it != 0) it.toString() else "" }
                    ) {
                        uiState.eventSink(InputAge(it))
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(onClick = { showAddDialog = false }) {
                            Text("CANCEL")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            uiState.eventSink(AddArtist)
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
fun ArtistItem(artist: Artist, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = artist.name, style = MaterialTheme.typography.titleMedium)
            InfoRow(icon = Icons.Default.AccountBox, infoName = "Image", infoContent = artist.image)
            InfoRow(
                icon = Icons.Default.DateRange,
                infoName = "Age",
                infoContent = artist.age.toString()
            )
        }
    }
}

