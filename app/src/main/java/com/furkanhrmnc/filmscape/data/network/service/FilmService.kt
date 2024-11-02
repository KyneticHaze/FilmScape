package com.furkanhrmnc.filmscape.data.network.service

import com.furkanhrmnc.filmscape.data.network.dto.BaseResponse
import com.furkanhrmnc.filmscape.data.network.dto.MediaDTO
import com.furkanhrmnc.filmscape.data.network.dto.detail.MediaDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.video.VideoResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FilmService : KtorApi() {
    suspend fun getMovieOrTv(
        type: String,
        category: String,
        page: Int = 1,
    ) = client.get {
        pathUrl("$type/$category")
        parameter("page", page)
    }.body<BaseResponse<MediaDTO>>()

    suspend fun getDetailMovieOrTv(
        type: String,
        movieId: Int,
    ) = client.get {
        pathUrl("$type/$movieId")
    }.body<MediaDetailDTO>()

    suspend fun getRecommendationsMovieOrTv(
        type: String,
        movieId: Int,
        page: Int = 1,
    ) = client.get {
        pathUrl("$type/$movieId/recommendations")
        parameter("page", page)
    }.body<BaseResponse<MediaDTO>>()

    suspend fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int = 1,
    ) = client.get {
        pathUrl("trending/$type/$timeWindow")
        parameter("page", page)
    }.body<BaseResponse<MediaDTO>>()

    suspend fun searchMovieOrTv(
        query: String,
        type: String,
        page: Int = 1,
    ) = client.get {
        pathUrl("search/$type")
        parameter("query", query)
        parameter("page", page)
    }.body<BaseResponse<MediaDTO>>()

    suspend fun getVideosMovieOrTv(
        type: String,
        movieId: Int,
    ) = client.get {
        pathUrl("$type/$movieId/videos")
    }.body<VideoResponse>()
}