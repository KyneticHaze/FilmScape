package com.example.movieland.data.remote.api

import com.example.movieland.data.remote.dto.media.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {
    @GET("{type}/{category}")
    suspend fun getMedias(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("trending/{type}/{time_window}")
    suspend fun getTrendingMedias(
        @Path("type") type: String,
        @Path("time_window") time: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ) : MediaResponse

    @GET("search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ) : MediaResponse
}