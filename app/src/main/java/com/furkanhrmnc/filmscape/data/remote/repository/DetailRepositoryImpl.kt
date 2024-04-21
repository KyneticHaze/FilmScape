package com.furkanhrmnc.filmscape.data.remote.repository

import com.furkanhrmnc.filmscape.util.Screens
import com.furkanhrmnc.filmscape.util.Resource
import com.furkanhrmnc.filmscape.data.local.database.MediaDao
import com.furkanhrmnc.filmscape.data.local.database.MediaEntity
import com.furkanhrmnc.filmscape.data.mapper.toMedia
import com.furkanhrmnc.filmscape.data.mapper.toMediaEntity
import com.furkanhrmnc.filmscape.data.remote.api.DetailApi
import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.Exception

class DetailRepositoryImpl @Inject constructor(
    private val mediaApi: DetailApi,
    private val mediaDao: MediaDao
) : DetailRepository {
    override suspend fun getDetailFilmOrTvSeries(
        type: String,
        id: Int,
        isRefresh: Boolean,
        apiKey: String
    ): Flow<Resource<Movie>> {
        return flow {

            emit(Resource.Loading(true))

            val detailEntity = mediaDao.selectMediaById(id)

            val isThereDetail =
                !(detailEntity.status == null || detailEntity.runtime == null || detailEntity.tagline == null)

            if (!isRefresh && isThereDetail) {
                emit(
                    Resource.Success(
                        data = detailEntity.toMedia(
                            type = detailEntity.mediaType,
                            category = detailEntity.category
                        )
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteMediaDetail = try {
                mediaApi.getDetailFilmOrTvSeries(type, id, apiKey)
            } catch (e: HttpException) {
                e.printStackTrace()
                when (e.code()) {
                    400 -> emit(Resource.Error(FilmException.EXCEPTION_400))
                    401 -> emit(Resource.Error(FilmException.EXCEPTION_401))
                    403 -> emit(Resource.Error(FilmException.EXCEPTION_403))
                    404 -> emit(Resource.Error(FilmException.EXCEPTION_404))
                    500 -> emit(Resource.Error(FilmException.EXCEPTION_500))
                    503 -> emit(Resource.Error(FilmException.EXCEPTION_503))
                    else -> emit(Resource.Error("Bir hata oluştu. Lütfen daha sonra tekrar deneyin."))
                }
                emit(Resource.Loading(false))
                return@flow
            } catch (e: IOException) {
                emit(Resource.Error(FilmException.EXCEPTION_IO))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaDetail.let { detailDto ->

                if (detailDto.runtime != null || detailDto.status != null || detailDto.tagline != null) {
                    detailEntity.runtime = detailDto.runtime!!
                    detailEntity.status = detailDto.status!!
                    detailEntity.tagline = detailDto.tagline!!
                }

                mediaDao.updateMedia(detailEntity)

                emit(
                    Resource.Success(
                        data = detailEntity.toMedia(
                            type = detailEntity.mediaType,
                            category = detailEntity.category
                        )
                    )
                )

                emit(Resource.Loading(false))
                return@flow
            }
        }
    }

    override suspend fun getSimilarMedias(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val similarEntity = mediaDao.selectMediaById(id)

            val isThereSimilarMediaList =
                (similarEntity.similarMediaList != null && similarEntity.similarMediaList != "-1,-2")

            if (!isRefresh && isThereSimilarMediaList) {

                try {
                    val similarMediaListIDs =
                        similarEntity.similarMediaList.split(",").map { it.toInt() }

                    val similarMediaList = ArrayList<MediaEntity>()

                    for (index in similarMediaListIDs.indices) {
                        similarMediaList.add(mediaDao.selectMediaById(similarMediaListIDs[index]))
                    }

                    emit(
                        Resource.Success(
                            data = similarMediaList.map {
                                it.toMedia(
                                    type = it.mediaType ?: Screens.MOVIE,
                                    category = it.category ?: Screens.POPULAR
                                )
                            }
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.Error(errorMessage = e.message ?: "Something went wrong"))
                }


                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSimilarList = try {
                mediaApi.getSimilarMedias(type, id, page, apiKey).medias
            } catch (e: HttpException) {
                e.printStackTrace()
                when (e.code()) {
                    400 -> emit(Resource.Error(FilmException.EXCEPTION_400))
                    401 -> emit(Resource.Error(FilmException.EXCEPTION_401))
                    403 -> emit(Resource.Error(FilmException.EXCEPTION_403))
                    404 -> emit(Resource.Error(FilmException.EXCEPTION_404))
                    500 -> emit(Resource.Error(FilmException.EXCEPTION_500))
                    503 -> emit(Resource.Error(FilmException.EXCEPTION_503))
                    else -> emit(Resource.Error("Bir hata oluştu. Lütfen daha sonra tekrar deneyin."))
                }
                emit(Resource.Loading(false))
                return@flow
            } catch (e: IOException) {
                emit(Resource.Error(FilmException.EXCEPTION_IO))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteSimilarList?.let { mediaDtos ->

                val similarMediaListIDs = ArrayList<Int>()

                for (i in mediaDtos.indices) {
                    similarMediaListIDs.add(mediaDtos[i].id ?: -1)
                }

                // similarMediaList değerine yeni oluşturduğum id listesini ekledim
                similarEntity.similarMediaList = try {
                    similarMediaListIDs.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                val cacheSimilarMediaList = remoteSimilarList.map {
                    it.toMediaEntity(
                        type = it.type ?: Screens.MOVIE,
                        category = it.category ?: Screens.POPULAR
                    )
                }

                mediaDao.insertMediaList(cacheSimilarMediaList)
                mediaDao.updateMedia(similarEntity)

                emit(
                    Resource.Success(
                        data = cacheSimilarMediaList.map {
                            it.toMedia(
                                type = it.mediaType ?: Screens.MOVIE,
                                category = it.category ?: Screens.POPULAR
                            )
                        }
                    )
                )

                emit(Resource.Loading(false))
                return@flow
            }
        }
    }

    override suspend fun getDetailCast(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Cast>> {
        return flow {
            // ?
        }
    }

    override suspend fun getVideos(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading(true))

            val videoEntity = mediaDao.selectMediaById(id)

            videoEntity.videos.let {
                if (!isRefresh) {
                    try {
                        val videoIds = it.split(",")
                        emit(Resource.Success(data = videoIds))
                    } catch (e: Exception) {
                        emit(Resource.Error("Not Found Video ID"))
                    }
                    emit(Resource.Loading(false))
                    return@flow
                }
            }

            val remoteVideoIds = try {
                mediaApi.getVideos(
                    type = videoEntity.mediaType,
                    id = id,
                    apiKey = apiKey
                ).videos?.filter { video ->
                    video.site == "YouTube" && video.type == "Featurette" || video.type == "Teaser"
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                when (e.code()) {
                    400 -> emit(Resource.Error(FilmException.EXCEPTION_400))
                    401 -> emit(Resource.Error(FilmException.EXCEPTION_401))
                    403 -> emit(Resource.Error(FilmException.EXCEPTION_403))
                    404 -> emit(Resource.Error(FilmException.EXCEPTION_404))
                    500 -> emit(Resource.Error(FilmException.EXCEPTION_500))
                    503 -> emit(Resource.Error(FilmException.EXCEPTION_503))
                    else -> emit(Resource.Error("Bir hata oluştu. Lütfen daha sonra tekrar deneyin."))
                }
                emit(Resource.Loading(false))
                return@flow
            } catch (e: IOException) {
                emit(Resource.Error(FilmException.EXCEPTION_IO))
                emit(Resource.Loading(false))
                return@flow
            }

            val videoWatchKeys = remoteVideoIds?.map { it.key }

            videoWatchKeys?.let {

                mediaDao.updateMedia(videoEntity)

                emit(
                    Resource.Success(
                        data = videoWatchKeys
                    )
                )

                emit(Resource.Loading(false))
            }
        }
    }
}