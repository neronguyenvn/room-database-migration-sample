package com.example.appmusic.core.data

import com.example.appmusic.core.database.model.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {

    fun getArtistsStream(): Flow<List<Artist>>

    suspend fun addArtist(artist: Artist)
}
