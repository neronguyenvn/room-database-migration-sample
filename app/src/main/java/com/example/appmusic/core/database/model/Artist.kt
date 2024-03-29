package com.example.appmusic.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(
    val name: String = "",
    val image: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
