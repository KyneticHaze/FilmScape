package com.furkanhrmnc.filmscape.data.data_source

import com.furkanhrmnc.filmscape.data.network.service.FilmService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val filmService: FilmService,
    private val dispatcher: Dispatchers,
) {
    suspend fun getMovieOrTv(
        type: String,
        category: String,
        page: Int = 1,
    ) = withContext(dispatcher.IO) {
        filmService.getMovieOrTv(type, category, page)
    }

    suspend fun getDetailMovieOrTv(type: String, id: Int) = withContext(dispatcher.IO) {
        filmService.getDetailMovieOrTv(type, id)
    }

    suspend fun getRecommendationsMovieOrTv(type: String, id: Int, page: Int = 1) =
        withContext(dispatcher.IO) {
            filmService.getRecommendationsMovieOrTv(type, id, page)
        }

    suspend fun searchMovieOrTv(query: String, type: String, page: Int = 1) =
        withContext(dispatcher.IO) {
            filmService.searchMovieOrTv(query, type, page)
        }

    suspend fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int = 1,
    ) = withContext(dispatcher.IO) {
        filmService.getTrendingMovieOrTv(type, timeWindow, page)
    }

    suspend fun getVideosMovieOrTv(type: String, id: Int) = withContext(dispatcher.IO) {
        filmService.getVideosMovieOrTv(type, id)
    }

    suspend fun getCreditsMovieOrTv(type: String, id: Int) = withContext(dispatcher.IO) {
        filmService.getCreditsMovieOrTv(type, id)
    }

    suspend fun getPopularPersons(page: Int = 1) = withContext(dispatcher.IO) {
        filmService.getPopularPersons(page)
    }

    suspend fun getPersonDetails(id: Int) = withContext(dispatcher.IO) {
        filmService.getPersonDetails(id)
    }
}