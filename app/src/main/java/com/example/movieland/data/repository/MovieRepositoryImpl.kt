package com.example.movieland.data.repository

import com.example.movieland.data.remote.dto.credit.CreditResponse
import com.example.movieland.data.remote.dto.detail.DetailDTO
import com.example.movieland.data.remote.dto.commonDto.MovieResponse
import com.example.movieland.data.remote.dto.image.ImageResponse
import com.example.movieland.data.remote.dto.playing_upComing.PlayUpComingResponse
import com.example.movieland.data.remote.dto.person.PersonResponse
import com.example.movieland.data.remote.service.ApiService
import com.example.movieland.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val service: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(
        lang: String, page: Int, token: String
    ): MovieResponse = service.getPopularMovies(lang, page, token)

    override suspend fun getTopRatedMovies(
        lang: String, page: Int, token: String
    ): MovieResponse = service.getTopRatedMovies(lang, page, token)

    override suspend fun getSimilarMovies(
        movieId: Int,
        lang: String,
        page: Int,
        token: String
    ): MovieResponse = service.getSimilarMovies(movieId, lang, page, token)

    override suspend fun getUpComingMovies(
        lang: String,
        page: Int,
        token: String
    ): PlayUpComingResponse =
        service.getUpcomingMovies(lang, page, token)

    override suspend fun getNowPlayingMovies(
        lang: String,
        page: Int,
        token: String
    ): PlayUpComingResponse =
        service.getNowPlayingMovies(lang, page, token)

    override suspend fun getMovieDetail(movieId: Int, token: String): DetailDTO =
        service.getMovieDetail(movieId, token)

    override suspend fun getMovieCredits(movieId: Int, token: String): CreditResponse =
        service.getMovieCredits(movieId, token)

    override suspend fun getPersonPopular(lang: String, page: Int, token: String): PersonResponse =
        service.getPersonPopular(lang, page, token)

    override suspend fun getSearchMovie(query: String, page: Int, token: String): MovieResponse =
        service.getSearchMovie(query, page, token)

    override suspend fun getMovieImage(movieId: Int, page: Int, token: String): ImageResponse =
        service.getMovieImage(movieId, page, token)
}