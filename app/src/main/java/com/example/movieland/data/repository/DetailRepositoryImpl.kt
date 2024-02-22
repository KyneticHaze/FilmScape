package com.example.movieland.data.repository

import com.example.movieland.core.Resource
import com.example.movieland.data.local.MediaDao
import com.example.movieland.data.mappers.toMedia
import com.example.movieland.data.remote.api.DetailApi
import com.example.movieland.data.remote.dto.images.Poster
import com.example.movieland.domain.model.Media
import com.example.movieland.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepositoryImpl(
    private val detailApi: DetailApi,
    private val dao: MediaDao
) : DetailRepository {
    override suspend fun getMediaDetail(
        isRefresh: Boolean,
        mediaId: Int,
        apiKey: String
    ): Flow<Resource<Media>> {
        return flow {

            emit(Resource.Loading(true))

            // Veritabanına kayıtlı olan objeyi alma
            val mediaEntity = dao.getMediaById(mediaId)

            if (!isRefresh) {
                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteDetails = detailApi.getMovieDetail(mediaId, apiKey)


            emit(
                Resource.Success(
                    data = mediaEntity.toMedia()
                )
            )

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getSimilarMedias(
        mediaId: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val remoteSimilarMedias = detailApi.getSimilarMedias(mediaId, page, apiKey).medias

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