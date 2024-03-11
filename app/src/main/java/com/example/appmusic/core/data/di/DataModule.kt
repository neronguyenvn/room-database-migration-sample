package com.example.appmusic.core.data.di

import com.example.appmusic.core.data.ArtistRepository
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.data.implementation.DefaultArtistRepository
import com.example.appmusic.core.data.implementation.DefaultSongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsArtistRepository(
        artistRepository: DefaultArtistRepository
    ): ArtistRepository

    @Binds
    abstract fun bindsSongRepository(
        songRepository: DefaultSongRepository
    ): SongRepository
}
