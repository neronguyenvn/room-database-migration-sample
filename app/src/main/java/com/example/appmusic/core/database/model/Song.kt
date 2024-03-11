package com.example.appmusic.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "song",
    foreignKeys = [ForeignKey(
        entity = Artist::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("artistId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("artistId", unique = true)]
)
data class Song(
    val name: String,
    val image: String,
    val artistId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
