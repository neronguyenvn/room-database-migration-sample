package com.example.appmusic.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appmusic.core.database.model.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun observeAll(): Flow<List<Artist>>

    @Insert
    fun insert(artist: Artist)
}
