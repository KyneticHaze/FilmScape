package com.furkanhrmnc.filmscape.data.remote.api

import com.furkanhrmnc.filmscape.data.remote.dto.detail.DetailDTO
import com.furkanhrmnc.filmscape.data.remote.dto.media.MediaResponse
import com.furkanhrmnc.filmscape.data.remote.dto.videos.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("{type}/{id}")
    suspend fun getDetailFilmOrTvSeries(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): DetailDTO

    @GET("{type}/{id}/similar")
    suspend fun getSimilarMedias(
        @Path("type") type: String,
        @Path("id") id : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaResponse

    @GET("{type}/{id}/videos")
    suspend fun getVideos(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): VideoResponse
}