package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.data.data_source.RemoteDataSource
import com.furkanhrmnc.filmscape.data.network.dto.BaseResponse
import com.furkanhrmnc.filmscape.data.network.dto.CreditResponse
import com.furkanhrmnc.filmscape.data.network.dto.MediaDTO
import com.furkanhrmnc.filmscape.data.network.dto.MediaDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.mapToModel
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.PersonDetailDTO
import com.furkanhrmnc.filmscape.data.network.dto.person.toPersonDetail
import com.furkanhrmnc.filmscape.data.network.dto.toCastList
import com.furkanhrmnc.filmscape.data.network.dto.toPagingMedia
import com.furkanhrmnc.filmscape.data.network.dto.toPagingPerson
import com.furkanhrmnc.filmscape.data.network.dto.video.VideoResponse
import com.furkanhrmnc.filmscape.data.network.dto.video.toVideoList
import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail
import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.domain.model.Person
import com.furkanhrmnc.filmscape.domain.model.PersonDetail
import com.furkanhrmnc.filmscape.domain.model.Video
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.TYPE_FEATURETTE
import com.furkanhrmnc.filmscape.util.Constants.TYPE_TRAILER
import com.furkanhrmnc.filmscape.util.Constants.YOUTUBE
import com.furkanhrmnc.filmscape.util.Response
import com.furkanhrmnc.filmscape.util.asResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MediaRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MediaRepository {
    override fun getMovieOrTv(
        type: String,
        category: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getMovieOrTv(type = type, category = category, page = page)
            .also { response -> emit(response) }
    }
        .map(BaseResponse<MediaDTO>::toPagingMedia)
        .asResponse()

    override fun getDetailMediaOrTv(type: String, id: Int): Flow<Response<MediaDetail>> = flow {
        remoteDataSource
            .getDetailMovieOrTv(type = type, id = id)
            .also { response -> emit(response) }
    }.map(MediaDetailDTO::mapToModel).asResponse()

    override fun getRecommendationsMovieOrTv(
        type: String,
        id: Int,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getRecommendationsMovieOrTv(type = type, id = id, page = page)
            .also { response -> emit(response) }
    }
        .map(BaseResponse<MediaDTO>::toPagingMedia)
        .asResponse()

    override fun searchMovieOrTv(
        query: String,
        type: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>> = flow {
        remoteDataSource
            .searchMovieOrTv(
                query = query,
                type = type,
                page = page
            )
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResponse()

    override fun getTrendingMovieOrTv(
        type: String,
        timeWindow: String,
        page: Int,
    ): Flow<Response<PaginatedData<Media>>> = flow {
        remoteDataSource
            .getTrendingMovieOrTv(
                type = type,
                timeWindow = timeWindow,
                page = page
            )
            .also { response -> emit(response) }
    }.map(BaseResponse<MediaDTO>::toPagingMedia).asResponse()

    override fun getVideosMovieOrTv(type: String, id: Int): Flow<Response<List<Video>>> = flow {
        remoteDataSource
            .getVideosMovieOrTv(
                type = type,
                id = id
            )
            .also { response -> emit(response) }
    }
        .map(VideoResponse::toVideoList)
        .map { videos -> videos.filter { it.site == YOUTUBE && (it.type == TYPE_FEATURETTE || it.type == TYPE_TRAILER) } }
        .asResponse()

    override fun getCreditsMovieOrTv(type: String, id: Int): Flow<Response<List<Cast>>> = flow {
        remoteDataSource
            .getCreditsMovieOrTv(
                type = type,
                id = id
            )
            .also { response -> emit(response) }
    }
        .map(CreditResponse::toCastList)
        .map { casts -> casts.filter { true } }
        .asResponse()

    override fun getPopularPersons(page: Int): Flow<Response<PaginatedData<Person>>> = flow {
        remoteDataSource
            .getPopularPersons(page)
            .also { response -> emit(response) }
    }
        .map(BaseResponse<PersonDTO>::toPagingPerson)
        .asResponse()

    override fun getPersonDetails(id: Int): Flow<Response<PersonDetail>> = flow {
        remoteDataSource
            .getPersonDetails(id)
            .also { response -> emit(response) }
    }
        .map(PersonDetailDTO::toPersonDetail)
        .asResponse()
}