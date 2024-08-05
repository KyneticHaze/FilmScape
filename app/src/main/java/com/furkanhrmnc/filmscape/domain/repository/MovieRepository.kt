package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.model.Videos
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.domain.model.details.credit.Credits
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

    fun getMovieCredits(movieId: Int): Flow<Credits>

    fun getMovieVideos(movieId: Int): Flow<Videos<Video>>
}