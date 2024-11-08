package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.model.Person
import com.furkanhrmnc.filmscape.domain.model.PersonDetail
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Data fonksiyonlarını tanımlayan arayüz.
 *
 * @author Furkan Harmancı
 */
interface MediaRepository {

    fun getMovieOrTv(
        type: String,
        category: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>>

    fun getDetailMediaOrTv(
        type: String,
        id: Int,
    ): Flow<Response<MediaDetail>>

    fun getRecommendationsMovieOrTv(
        type: String,
        id: Int,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>>

    fun searchMovieOrTv(
        query: String,
        type: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>>

    fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>>

    fun getVideosMovieOrTv(
        type: String,
        id: Int,
    ): Flow<Response<List<Video>>>

    fun getCreditsMovieOrTv(
        type: String,
        id: Int,
    ): Flow<Response<List<Cast>>>

    fun getPopularPersons(
        page: Int,
    ): Flow<Response<PaginatedData<Person>>>

    fun getPersonDetails(id: Int): Flow<Response<PersonDetail>>
}