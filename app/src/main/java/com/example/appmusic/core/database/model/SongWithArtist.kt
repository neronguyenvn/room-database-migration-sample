package com.example.appmusic.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class SongWithArtist(
    @Embedded
    val song: Song,

    @Relation(
        parentColumn = "artistId",
        entityColumn = "id"
    )
    val artist: Artist
)