package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(
        category: String,
        page: Int
    ): Flow<PagingMovie<Movie>>

    fun searchMovie(
        query: String,
        page: Int
    ): Flow<PagingMovie<Movie>>
}