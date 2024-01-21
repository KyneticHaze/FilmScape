package com.example.movieland.domain.repository

import com.example.movieland.core.Resource
import com.example.movieland.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getAnythings(
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingAnything(
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getSearchAnything(
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>
}