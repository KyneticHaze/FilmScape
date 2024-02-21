package com.example.movieland.data.remote.api

import com.example.movieland.data.remote.dto.media.MediaResponse
import com.example.movieland.data.remote.dto.images.ImageResponse
import com.example.movieland.data.remote.dto.detail.DetailResponse
import com.example.movieland.data.remote.dto.videos.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("{type}/{id}")
    suspend fun getMovieDetail(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): DetailResponse

    @GET("{type}/{id}/similar")
    suspend fun getSimilarMedias(
        @Path("type") type: String,
        @Path("id") id : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("{type}/{id}/images")
    suspend fun getMediaImages(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ) : ImageResponse

    @GET("{type}/{id}/videos")
    suspend fun getMediaVideos(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): VideoResponse
}