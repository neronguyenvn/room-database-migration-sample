package com.example.appmusic.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP INDEX index_song_artistId")
        db.execSQL("CREATE INDEX index_song_artistId ON song (artistId)")
    }
}