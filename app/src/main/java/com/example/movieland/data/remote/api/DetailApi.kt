package com.example.movieland.data.remote.api

import com.example.movieland.data.remote.dto.DetailDTO
import com.example.movieland.data.remote.dto.movie.MediaResponse
import com.example.movieland.data.remote.dto.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @GET("{type}/{movie_id}")
    suspend fun getMovieDetail(
        @Path("type") type: String,
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String
    ): DetailDTO

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("{type}/{type_id}/images")
    suspend fun getMovieImage(
        @Path("type") type: String,
        @Path("type_id") typeId: Int,
        @Query("api_key") token: String
    ) : ImageResponse
}