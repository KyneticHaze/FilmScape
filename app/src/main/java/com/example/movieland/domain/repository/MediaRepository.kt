package com.example.movieland.domain.repository

import com.example.movieland.core.Resource
import com.example.movieland.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    // Room functions
    suspend fun getMedia(
        id: Int,
        type: String,
        category: String
    ): Media

    suspend fun insertMedia(media: Media)

    suspend fun updateMedia(media: Media)


    // Retrofit functions
    suspend fun getAnythings(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingAnything(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getSearchAnything(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>
}