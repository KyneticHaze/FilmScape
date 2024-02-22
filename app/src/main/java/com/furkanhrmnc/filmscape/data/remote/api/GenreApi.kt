package com.furkanhrmnc.filmscape.data.remote.api

import com.furkanhrmnc.filmscape.data.remote.dto.genre.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenreApi {
    @GET("genre/{type}/list")
    suspend fun getGenres(
        @Path("type") type: String,
        @Query("api_key") apiKey: String
    ): GenreResponse
}