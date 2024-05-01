package com.furkanhrmnc.filmscape.data.remote.repository

import com.furkanhrmnc.filmscape.data.remote.api.MovieApi
import com.furkanhrmnc.filmscape.data.remote.dto.media.MovieResponse
import com.furkanhrmnc.filmscape.data.remote.dto.media.toPagingMovie
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.PagingMovie
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

    override fun searchMovie(query: String, page: Int): Flow<PagingMovie<Movie>> = flow {
        api.searchMovie(
            query = query,
            page = page
        ).also { response -> emit(value = response)}
    }.map(MovieResponse::toPagingMovie)
}