package com.example.appmusic.core.data

import com.example.appmusic.core.database.model.Song
import com.example.appmusic.core.database.model.SongWithArtist
import kotlinx.coroutines.flow.Flow

interface SongRepository {

    fun getFavoriteSongsStream(): Flow<List<SongWithArtist>>

    suspend fun addSong(song: Song)

    suspend fun setFavorite(songId: Int)
}
