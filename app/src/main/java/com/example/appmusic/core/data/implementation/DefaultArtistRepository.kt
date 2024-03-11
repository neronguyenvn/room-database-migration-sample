package com.example.appmusic.core.data.implementation

import com.example.appmusic.core.common.coroutine.AmDispatcher.IO
import com.example.appmusic.core.common.coroutine.Dispatcher
import com.example.appmusic.core.data.ArtistRepository
import com.example.appmusic.core.database.ArtistDao
import com.example.appmusic.core.database.model.Artist
import com.example.appmusic.core.database.model.ArtistWithSongs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultArtistRepository @Inject constructor(
    private val artistDao: ArtistDao,

    @Dispatcher(IO)
    private val ioDispatcher: CoroutineDispatcher
) : ArtistRepository {

    override fun getArtistsStream(): Flow<List<Artist>> {
        return artistDao.observeAll()
    }

    override fun getArtistWithSongsStream(artistId: Int): Flow<ArtistWithSongs?> {
        return artistDao.observeWithSongsById(artistId)
    }

    override suspend fun addArtist(artist: Artist) {
        withContext(ioDispatcher) {
            artistDao.insert(artist)
        }
    }
}
