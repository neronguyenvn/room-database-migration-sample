package com.example.appmusic.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appmusic.core.database.ArtistDao
import com.example.appmusic.core.database.SongDao
import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.core.database.model.Song

@Database(
    entities = [
        Artist::class,
        Song::class
    ],
    version = 2,
    exportSchema = true
)
abstract class RoomAmDatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    abstract fun songDao(): SongDao
}
