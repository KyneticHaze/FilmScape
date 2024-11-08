package com.furkanhrmnc.filmscape.data.network.service

import com.furkanhrmnc.filmscape.data.network.dto.BaseResponse
import com.furkanhrmnc.filmscape.data.network.dto.CreditResponse
import com.furkanhrmnc.filmscape.data.network.dto.MediaDTO
import com.furkanhrmnc.filmscape.data.network.dto.MediaDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDetailDTO
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
        id: Int,
    ) = client.get {
        pathUrl("$type/$id")
    }.body<MediaDetailDTO>()

    suspend fun getRecommendationsMovieOrTv(
        type: String,
        id: Int,
        page: Int = 1,
    ) = client.get {
        pathUrl("$type/$id/recommendations")
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
        id: Int,
    ) = client.get {
        pathUrl("$type/$id/videos")
    }.body<VideoResponse>()

    suspend fun getCreditsMovieOrTv(
        type: String,
        id: Int,
    ) = client.get {
        pathUrl("$type/$id/credits")
    }.body<CreditResponse>()

    suspend fun getPopularPersons(page: Int = 1) = client.get {
        pathUrl("person/popular")
        parameter("page", page)
    }.body<BaseResponse<PersonDTO>>()

    suspend fun getPersonDetails(id: Int) = client.get {
        pathUrl("person/$id")
    }.body<PersonDetailDTO>()
}