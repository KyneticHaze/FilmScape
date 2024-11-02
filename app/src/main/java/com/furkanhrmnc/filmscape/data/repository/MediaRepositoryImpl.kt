package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.data.data_source.LocalDataSource
import com.furkanhrmnc.filmscape.data.data_source.RemoteDataSource
import com.furkanhrmnc.filmscape.data.network.dto.BaseResponse
import com.furkanhrmnc.filmscape.data.network.dto.MediaDTO
import com.furkanhrmnc.filmscape.data.network.dto.detail.MediaDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.detail.mapToModel
import com.furkanhrmnc.filmscape.data.network.dto.toPagingMedia
import com.furkanhrmnc.filmscape.data.network.dto.video.VideoResponse
import com.furkanhrmnc.filmscape.data.network.dto.video.toVideoList
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.TYPE_TRAILER
import com.furkanhrmnc.filmscape.util.Result
import com.furkanhrmnc.filmscape.util.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MediaRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MediaRepository {
    override fun getMovieOrTv(
        type: String,
        category: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getMovieOrTv(type = type, category = category, page = page)
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResult()

    override fun getDetailMediaOrTv(type: String, id: Int): Flow<Result<MediaDetail>> = flow {
        remoteDataSource
            .getDetailMovieOrTv(type = type, movieId = id)
            .also { response -> emit(response) }
    }.map(MediaDetailDTO::mapToModel).asResult()

    override fun getRecommendationsMovieOrTv(
        type: String,
        id: Int,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getRecommendationsMovieOrTv(type = type, movieId = id, page = page)
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResult()

    override fun searchMovieOrTv(
        query: String,
        type: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>> = flow {
        remoteDataSource
            .searchMovieOrTv(
                query = query,
                type = type,
                page = page
            )
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResult()

    override fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int,
    ): Flow<Result<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getTrendingMovieOrTv(
                type = type,
                timeWindow = timeWindow,
                page = page
            )
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResult()

    override fun getVideosMovieOrTv(type: String, id: Int): Flow<Result<Video>> = flow {
        remoteDataSource
            .getVideosMovieOrTv(
                type = type,
                movieId = id
            )
            .also { response -> emit(response) }
    }
        .map(VideoResponse::toVideoList)
        .map { videos -> videos.first { video -> video.type == TYPE_TRAILER } }
        .asResult()

    override fun getMediaByIdFromCache(id: Int): Flow<Media> = flow {
        localDataSource.getMovieByIdFromCache(id = id)
            .also { response -> emit(response) }
    }

    override suspend fun addMediaToCache(media: Media) {
        localDataSource.addMovieToCache(media)
    }

    override suspend fun updateMediaInCache(media: Media) {
        localDataSource.updateMovieInCache(media)
    }

    override suspend fun deleteMediaInCache(media: Media) {
        localDataSource.deleteMovieInCache(media)
    }
}