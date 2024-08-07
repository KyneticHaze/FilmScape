package com.furkanhrmnc.filmscape.data.remote.repository

import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import com.furkanhrmnc.filmscape.data.remote.dto.details.MovieDetailsDTO
import com.furkanhrmnc.filmscape.data.remote.dto.details.credit.CreditResponse
import com.furkanhrmnc.filmscape.data.remote.dto.details.credit.toCredit
import com.furkanhrmnc.filmscape.data.remote.dto.details.toMovieDetails
import com.furkanhrmnc.filmscape.data.remote.dto.movie.MovieResponse
import com.furkanhrmnc.filmscape.data.remote.dto.movie.toPagingMovie
import com.furkanhrmnc.filmscape.data.remote.dto.videos.VideoResponse
import com.furkanhrmnc.filmscape.data.remote.dto.videos.toVideos
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.model.Videos
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.domain.model.details.credit.Credits
import com.furkanhrmnc.filmscape.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Api'deki fonksiyonları kullanan fonksiyonları repoImpl'de oluşturuyoruz.
 *
 * Repodaki fonksiyonlar başka yerlerde, api fonksiyonlarını daha rahat işlemek için yaratıldı.
 *
 * Repo fonksiyon parametrelerine gelecek değerleri başka bir fonksiyonda verebiliriz.
 *
 * @author Furkan Harmancı
 */
class MovieRepositoryImpl(
    private val api: MovieApi,
) : MovieRepository {
    override fun getMovies(category: String, page: Int): Flow<PagingMovie<Movie>> = flow {
        api.getMovies(
            category = category,
            page = page
        ).also { response -> emit(value = response) }
    }.map(MovieResponse::toPagingMovie)

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> = flow {
        api.getMovieDetails(movieId)
            .also { response -> emit(value = response) }
    }.map(MovieDetailsDTO::toMovieDetails)

    override fun getMovieRecommendations(movieId: Int, page: Int): Flow<PagingMovie<Movie>> = flow {
        api.getMovieRecommendations(
            id = movieId,
            page = page
        ).also { response -> emit(value = response) }
    }.map(MovieResponse::toPagingMovie)

    override fun searchMovie(query: String, page: Int): Flow<PagingMovie<Movie>> = flow {
        api.searchMovie(
            query = query,
            page = page
        ).also { response -> emit(value = response) }
    }.map(MovieResponse::toPagingMovie)

    override fun getMovieCredits(movieId: Int): Flow<Credits> = flow {
        api.getMovieCredits(movieId)
            .also { response -> emit(value = response) }
    }.map(CreditResponse::toCredit)

    override fun getMovieVideos(movieId: Int): Flow<Videos<Video>> = flow {
        api.getMovieVideos(movieId)
            .also { response -> emit(value = response) }
    }.map(VideoResponse::toVideos)
}