package com.example.movieland.data.service

import com.example.movieland.data.remote.dto.credit.CreditResponse
import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.commonDto.MovieResponse
import com.example.movieland.data.remote.dto.playing_upComing.PlayUpComingResponse
import com.example.movieland.data.remote.dto.person.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") lang: String = "en_US",
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en_US",
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId : Int,
        @Query("language") lang: String,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en_US",
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): PlayUpComingResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") lang: String,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ) : PlayUpComingResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String
    ): DetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String
    ): CreditResponse

    @GET("person/popular")
    suspend fun getPersonPopular(
        @Query("language") lang: String = "en_US",
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): PersonResponse
}