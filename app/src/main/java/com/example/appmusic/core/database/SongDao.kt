package com.example.appmusic.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.appmusic.core.database.model.Song
import com.example.appmusic.core.database.model.SongWithArtist
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {


    @Transaction
    @Query("SELECT * FROM song WHERE isFavorite = 1")
    fun observeByFavorite(): Flow<List<SongWithArtist>>

    @Upsert
    fun upsert(song: Song)

    @Query("UPDATE song SET isFavorite = NOT isFavorite WHERE id = :id")
    fun toggleIsFavoriteById(id: Int)
}
