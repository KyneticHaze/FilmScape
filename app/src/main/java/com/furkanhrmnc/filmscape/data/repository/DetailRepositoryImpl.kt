package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.common.Resource
import com.furkanhrmnc.filmscape.data.local.MediaDao
import com.furkanhrmnc.filmscape.data.mappers.toMedia
import com.furkanhrmnc.filmscape.data.remote.api.DetailApi
import com.furkanhrmnc.filmscape.data.remote.dto.images.Poster
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepositoryImpl(
    private val detailApi: DetailApi,
    private val dao: MediaDao
) : DetailRepository {
    override suspend fun getMediaDetail(
        type: String,
        mediaId: Int,
        apiKey: String
    ): Flow<Resource<Media>> {
        return flow {

            emit(Resource.Loading(true))

            // Veritabanına kayıtlı olan objeyi alma
            val mediaEntity = dao.getMediaById(mediaId)

            val remoteDetails = detailApi.getMovieDetail(type, mediaId, apiKey)


            emit(
                Resource.Success(
                    data = mediaEntity.toMedia()
                )
            )

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getSimilarMedias(
        type: String,
        mediaId: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val remoteSimilarMedias = detailApi.getSimilarMedias(type, mediaId, page, apiKey).medias

            if (remoteSimilarMedias == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteSimilarMedias.let { medias ->
                emit(
                    Resource.Success(
                        data = medias.map {
                            it.toMedia()
                        }
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getMediaImages(
        type: String,
        typeId: Int,
        apiKey: String
    ): Flow<Resource<List<Poster>>> {
        return flow {

            emit(Resource.Loading(true))

            val remoteMediaImages = detailApi.getMediaImages(type, typeId, apiKey).posters

            if (remoteMediaImages == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaImages.let { posters ->
                emit(
                    Resource.Success(
                        data = posters
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }
}