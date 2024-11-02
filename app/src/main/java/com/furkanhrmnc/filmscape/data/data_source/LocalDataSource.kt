package com.furkanhrmnc.filmscape.data.data_source

import com.furkanhrmnc.filmscape.data.local.MediaDao
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val mediaDao: MediaDao,
    private val dispatcher: Dispatchers,
) {
    suspend fun getMovieByIdFromCache(id: Int) = withContext(dispatcher.IO) {
        mediaDao.getMediaById(id)
    }

    suspend fun addMovieToCache(media: Media) = withContext(dispatcher.IO) {
        mediaDao.addMedia(media)
    }

    suspend fun updateMovieInCache(media: Media) = withContext(dispatcher.IO) {
        mediaDao.updateMedia(media)
    }

    suspend fun deleteMovieInCache(media: Media) = withContext(dispatcher.IO) {
        mediaDao.deleteMedia(media)
    }
}