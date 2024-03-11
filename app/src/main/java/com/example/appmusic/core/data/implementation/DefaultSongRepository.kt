package com.example.appmusic.core.data.implementation

import com.example.appmusic.core.common.coroutine.AmDispatcher.IO
import com.example.appmusic.core.common.coroutine.Dispatcher
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.database.SongDao
import com.example.appmusic.core.database.model.ArtistWithSongs
import com.example.appmusic.core.database.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultSongRepository @Inject constructor(
    private val songDao: SongDao,

    @Dispatcher(IO)
    private val ioDispatcher: CoroutineDispatcher
) : SongRepository {

    override fun getSongs(artistId: Int): Flow<ArtistWithSongs?> {
        return songDao.observeByArtistId(artistId)
    }

    override suspend fun addSong(song: Song) {
        withContext(ioDispatcher) {
            songDao.upsert(song)
        }
    }
}
