package com.example.appmusic.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.appmusic.core.database.migration.MIGRATION_1_2
import com.example.appmusic.core.database.migration.MIGRATION_2_3
import com.example.appmusic.core.database.migration.MIGRATION_3_4
import com.example.appmusic.core.database.migration.MIGRATION_4_5
import com.example.appmusic.core.database.room.RoomAmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomAmDatabase = Room.databaseBuilder(
        context,
        RoomAmDatabase::class.java,
        "am-database"
    )
        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4,
            MIGRATION_4_5
        )
        .build()

    @Provides
    fun providesArtistDao(
        database: RoomAmDatabase
    ) = database.artistDao()

    @Provides
    fun providesSongDao(
        database: RoomAmDatabase
    ) = database.songDao()
}
