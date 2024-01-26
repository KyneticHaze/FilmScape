package com.example.movieland.data.remote.api

import com.example.movieland.BuildConfig
import com.example.movieland.data.remote.dto.movie.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {
    @GET("{type}/{category}")
    suspend fun getAnythings(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("trending/{type}/{time_window}")
    suspend fun getTrendingAnything(
        @Path("type") type: String,
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ) : MediaResponse

    @GET("search/multi")
    suspend fun getSearchAnything(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ) : MediaResponse

     companion object {
         const val BASE_URL = "https://api.themoviedb.org/3/"
         const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
         const val API_KEY = BuildConfig.API_KEY
     }
}