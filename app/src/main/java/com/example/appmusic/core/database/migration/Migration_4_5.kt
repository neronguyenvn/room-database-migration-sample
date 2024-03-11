package com.example.appmusic.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE genre (name TEXT NOT NULL, image TEXT NOT NULL, id INTEGER NOT NULL, PRIMARY KEY (id))")
    }
}