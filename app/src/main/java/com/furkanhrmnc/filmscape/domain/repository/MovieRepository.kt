package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(
        category: String,
        page: Int
    ): Flow<PaginatedData<Movie>>

    suspend fun searchMovie(
        query: String,
        page: Int
    ): Flow<PaginatedData<Movie>>
}