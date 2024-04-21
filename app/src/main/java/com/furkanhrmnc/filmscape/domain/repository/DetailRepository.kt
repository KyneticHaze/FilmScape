package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.util.Resource
import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    suspend fun getDetailFilmOrTvSeries(
        type: String,
        id: Int,
        isRefresh: Boolean,
        apiKey: String
    ): Flow<Resource<Movie>>

    suspend fun getSimilarMedias(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>>

    suspend fun getDetailCast(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Cast>>

    suspend fun getVideos(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<List<String>>>
}