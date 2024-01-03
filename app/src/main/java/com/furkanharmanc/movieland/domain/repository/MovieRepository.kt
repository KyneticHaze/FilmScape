package com.furkanharmanc.movieland.domain.repository

import com.furkanharmanc.movieland.data.remote.dto.detail.DetailResponse
import com.furkanharmanc.movieland.data.remote.dto.popular.MoviesResponse

interface MovieRepository {

    suspend fun getPopularMovies(token : String) : MoviesResponse
    suspend fun getMovieDetail(movieId : Int, token: String) : DetailResponse
}