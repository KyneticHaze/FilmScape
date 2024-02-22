package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.common.Resource
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    // Room functions
    suspend fun getMedia(id: Int): Media

    suspend fun insertMedia(media: Media)

    // Retrofit functions
    suspend fun getMedias(
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingMedias(
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun search(
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>
}