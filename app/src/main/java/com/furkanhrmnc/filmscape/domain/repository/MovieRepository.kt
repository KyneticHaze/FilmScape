package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(
        category: String,
        page: Int
    ): Flow<PagingMovie<Movie>>

    fun getMovieDetails(movieId: Int): Flow<MovieDetails>

    fun searchMovie(
        query: String,
        page: Int
    ): Flow<PagingMovie<Movie>>
}