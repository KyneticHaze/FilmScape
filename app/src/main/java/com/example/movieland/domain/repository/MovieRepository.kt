package com.example.movieland.domain.repository

import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.popular.MoviesResponse

interface MovieRepository {

    suspend fun getPopularMovies(token : String) : MoviesResponse
    suspend fun getMovieDetail(movieId : Int) : DetailResponse
}