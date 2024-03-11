package com.example.appmusic.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(
    val name: String = "",
    val image: String = "",
    val age: Int = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
