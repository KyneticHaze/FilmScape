package com.furkanharmanc.movieland.data.repository

import com.furkanharmanc.movieland.data.remote.dto.detail.DetailResponse
import com.furkanharmanc.movieland.data.remote.dto.popular.MoviesResponse
import com.furkanharmanc.movieland.data.service.ApiService
import com.furkanharmanc.movieland.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val service: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(token: String): MoviesResponse = service.getPopularMovies(token)
    override suspend fun getMovieDetail(movieId: Int, token: String): DetailResponse = service.getMovieDetail(movieId, token)
}