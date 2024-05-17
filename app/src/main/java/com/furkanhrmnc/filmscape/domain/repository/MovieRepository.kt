package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import kotlinx.coroutines.flow.Flow

/**
 * Data fonksiyonlarını tanımlayan arayüz.
 *
 * @author Furkan Harmancı
 */
interface MovieRepository {

    fun getMovies(
        category: String,
        page: Int
    ): Flow<PagingMovie<Movie>>

    fun getMovieDetails(movieId: Int): Flow<MovieDetails>

    fun getMovieRecommendations(movieId: Int, page: Int): Flow<PagingMovie<Movie>>

    fun searchMovie(
        query: String,
        page: Int
    ): Flow<PagingMovie<Movie>>
}