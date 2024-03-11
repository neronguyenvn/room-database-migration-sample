package com.example.appmusic.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.core.database.model.ArtistWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun observeAll(): Flow<List<Artist>>

    @Transaction
    @Query("SELECT * FROM artist WHERE id = :artistId")
    fun observeWithSongsById(artistId: Int): Flow<ArtistWithSongs?>

    @Insert
    fun insert(artist: Artist)
}
