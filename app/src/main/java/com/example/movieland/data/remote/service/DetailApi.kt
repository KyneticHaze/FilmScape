package com.example.movieland.data.remote.service

import com.example.movieland.data.remote.dto.commonDto.MovieResponse
import com.example.movieland.data.remote.dto.credit.CreditResponse
import com.example.movieland.data.remote.dto.detail.DetailDTO
import com.example.movieland.data.remote.dto.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String
    ): DetailDTO

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId : Int,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): MovieResponse

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImage(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ) : ImageResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String
    ): CreditResponse
}