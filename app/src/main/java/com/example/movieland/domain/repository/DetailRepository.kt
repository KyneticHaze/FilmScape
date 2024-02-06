package com.example.movieland.domain.repository

import com.example.movieland.core.Resource
import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    suspend fun getMediaDetail(
        type: String,
        isRefresh: Boolean,
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