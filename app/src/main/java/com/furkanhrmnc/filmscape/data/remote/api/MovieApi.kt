package com.furkanhrmnc.filmscape.data.remote.api

import com.furkanhrmnc.filmscape.data.remote.dto.media.PaginatedDataDTO
import com.furkanhrmnc.filmscape.util.ApiConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ): PaginatedDataDTO

    @GET("search/multi")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = ApiConfig.API_KEY
    ) : PaginatedDataDTO
}