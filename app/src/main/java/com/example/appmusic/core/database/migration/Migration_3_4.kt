package com.example.appmusic.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        with(db) {
            execSQL("CREATE TABLE artist_backup (name TEXT NOT NULL, image TEXT NOT NULL, id INTEGER NOT NULL, PRIMARY KEY (id))")
            execSQL("INSERT INTO artist_backup SELECT name, image, id FROM artist")
            execSQL("DROP TABLE artist")
            execSQL("ALTER TABLE artist_backup RENAME to artist")
        }
    }
}