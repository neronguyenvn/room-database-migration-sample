package com.example.appmusic.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    val name: String,
    val image: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
