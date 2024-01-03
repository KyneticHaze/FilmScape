package com.example.movieland.data.repository

import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.popular.MoviesResponse
import com.example.movieland.data.service.ApiService
import com.example.movieland.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val service: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(token: String): MoviesResponse = service.getPopularMovies(token)
    override suspend fun getMovieDetail(movieId: Int, token: String): DetailResponse = service.getMovieDetail(movieId, token)
}