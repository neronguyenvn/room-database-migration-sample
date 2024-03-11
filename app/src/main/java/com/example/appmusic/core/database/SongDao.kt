package com.example.appmusic.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appmusic.core.database.model.ArtistWithSongs
import com.example.appmusic.core.database.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Query("SELECT * FROM artist WHERE id = :artistId")
    fun observeByArtistId(artistId: Int): Flow<ArtistWithSongs?>

    @Upsert
    fun upsert(song: Song)
}
