package com.example.movieland.data.remote.api

import com.example.movieland.data.remote.dto.DetailDTO
import com.example.movieland.data.remote.dto.media.MediaResponse
import com.example.movieland.data.remote.dto.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @GET("{type}/{media_id}")
    suspend fun getMediaDetails(
        @Path("type") type: String,
        @Path("media_id") id: Int,
        @Query("api_key") apiKey: String
    ): DetailDTO

    @GET("{type}/{media_id}/similar")
    suspend fun getSimilarMedias(
        @Path("type") type: String,
        @Path("media_id") mediaId : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("{type}/{type_id}/images")
    suspend fun getMediaImages(
        @Path("type") type: String,
        @Path("type_id") typeId: Int,
        @Query("api_key") token: String
    ) : ImageResponse
}