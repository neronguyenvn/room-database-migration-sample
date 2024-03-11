package com.example.appmusic.core.data

import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.core.database.model.ArtistWithSongs
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {

    fun getArtistsStream(): Flow<List<Artist>>

    fun getArtistWithSongsStream(artistId: Int): Flow<ArtistWithSongs?>

    suspend fun addArtist(artist: Artist)
}
