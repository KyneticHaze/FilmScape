package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.core.Resource
import com.furkanhrmnc.filmscape.data.remote.dto.images.Poster
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    suspend fun getMediaDetail(
        type: String,
        mediaId: Int,
        apiKey: String
    ): Flow<Resource<Media>>

    suspend fun getSimilarMedias(
        type: String,
        mediaId: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getMediaImages(
        type: String,
        typeId: Int,
        apiKey: String
    ): Flow<Resource<List<Poster>>>
}