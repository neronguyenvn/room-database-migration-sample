package com.example.appmusic.core.data.implementation

import com.example.appmusic.core.common.coroutine.AmDispatcher.IO
import com.example.appmusic.core.common.coroutine.Dispatcher
import com.example.appmusic.core.data.SongRepository
import com.example.appmusic.core.database.SongDao
import com.example.appmusic.core.database.model.Song
import com.example.appmusic.core.database.model.SongWithArtist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultSongRepository @Inject constructor(
    private val songDao: SongDao,

    @Dispatcher(IO)
    private val ioDispatcher: CoroutineDispatcher
) : SongRepository {

    override fun getFavoriteSongsStream(): Flow<List<SongWithArtist>> {
        return songDao.observeByFavorite()
    }

    override suspend fun addSong(song: Song) {
        withContext(ioDispatcher) {
            songDao.upsert(song)
        }
    }

    override suspend fun setFavorite(songId: Int) {
        withContext(ioDispatcher) {
            songDao.toggleIsFavoriteById(songId)
        }
    }
}
