package com.example.appmusic.core.data

import com.example.appmusic.core.database.model.ArtistWithSongs
import com.example.appmusic.core.database.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {

    fun getSongs(artistId: Int): Flow<ArtistWithSongs?>

    suspend fun addSong(song: Song)
}
