package com.example.movieland.domain.repository

import com.example.movieland.data.remote.dto.credit.CreditResponse
import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.commonDto.MovieResponse
import com.example.movieland.data.remote.dto.playing_upComing.PlayUpComingResponse
import com.example.movieland.data.remote.dto.person.PersonResponse

interface MovieRepository {

    suspend fun getPopularMovies(lang: String, page: Int, token: String): MovieResponse
    suspend fun getTopRatedMovies(lang: String, page: Int, token: String): MovieResponse
    suspend fun getSimilarMovies(movieId: Int, lang: String, page: Int, token: String): MovieResponse
    suspend fun getUpComingMovies(lang: String, page: Int, token: String): PlayUpComingResponse
    suspend fun getNowPlayingMovies(lang: String, page: Int, token: String): PlayUpComingResponse
    suspend fun getMovieDetail(movieId: Int, token: String): DetailResponse
    suspend fun getMovieCredits(movieId: Int, token: String): CreditResponse
    suspend fun getPersonPopular(lang: String, page: Int, token: String): PersonResponse
}