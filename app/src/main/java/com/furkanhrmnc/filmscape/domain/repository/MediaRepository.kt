package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.core.Resource
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    // Room functions
    suspend fun getMedia(id: Int): Media

    suspend fun insertMedia(media: Media)

    suspend fun updateMedia(media: Media)


    // Retrofit functions
    suspend fun getMedias(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingMedias(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun search(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>
}