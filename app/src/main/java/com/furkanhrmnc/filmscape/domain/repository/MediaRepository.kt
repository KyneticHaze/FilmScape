package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * Data fonksiyonlarını tanımlayan arayüz.
 *
 * @author Furkan Harmancı
 */
interface MediaRepository {

    fun getMovieOrTv(
        type: String,
        category: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>>

    fun getDetailMediaOrTv(
        type: String,
        id: Int,
    ): Flow<Result<MediaDetail>>

    fun getRecommendationsMovieOrTv(
        type: String,
        id: Int,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>>

    fun searchMovieOrTv(
        query: String,
        type: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>>

    fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>>

    fun getVideosMovieOrTv(
        type: String,
        id: Int,
    ): Flow<Result<Video>>

    fun getMediaByIdFromCache(id: Int): Flow<Media>

    suspend fun addMediaToCache(media: Media)

    suspend fun updateMediaInCache(media: Media)

    suspend fun deleteMediaInCache(media: Media)
}