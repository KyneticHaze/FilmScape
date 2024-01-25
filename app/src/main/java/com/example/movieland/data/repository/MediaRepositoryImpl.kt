package com.example.movieland.data.repository

import com.example.movieland.core.Constants.TRENDING
import com.example.movieland.core.Resource
import com.example.movieland.data.local.MediaDao
import com.example.movieland.data.mappers.toMedia
import com.example.movieland.data.mappers.toMediaEntity
import com.example.movieland.data.remote.api.MediaApi
import com.example.movieland.domain.model.Media
import com.example.movieland.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
    private val api: MediaApi,
    private val dao: MediaDao
) : MediaRepository {

    override suspend fun getMedia(id: Int, type: String, category: String): Media {
        return dao.getMediaById(id).toMedia(type, category)
    }

    override suspend fun insertMedia(media: Media) {
        val mediaEntity = media.toMediaEntity()
        return dao.insertMedia(mediaEntity)
    }

    override suspend fun updateMedia(media: Media) {
        val mediaEntity = media.toMediaEntity()
        return dao.updateMedia(mediaEntity)
    }

    override suspend fun getAnythings(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localMedias = dao.getMediasByTypeAndCategory(type, category)

            // verileri tabandan çekmek için
            val shouldLoadFromCache =
            /// local medias boş olmayacak
                /// uzaktan veri çekmemek ve yenilememek şartı ile
                localMedias.isNotEmpty() && !fetchFromRemote && !isRefresh

            if (shouldLoadFromCache) {
                emit(Resource.Success(
                    data = localMedias.map { it.toMedia(type, category) }
                ))
                emit(Resource.Loading(true))
                return@flow
            }

            var searchPage = page
            if (isRefresh) {
                /// Sayfa yenilendiğinde media verilerini sil ve arama sayfasını 1'e endekle
                dao.deleteMediaByTypeAndCategory(type, category)
                searchPage = 1
            }

            val mediaList = try {
                /// 1'e endekslenen sayfayı veri çekme fonksiyonunda yerleştir.
                /// sayfa yenilenirse 1 yenilenmez ise verilen sayfadan veri çeker.
                api.getAnythings(type, category, searchPage, apiKey).medias
            } catch (e: IOException) {
                emit(Resource.Error(errorMessage = e.localizedMessage ?: "Couldn't load data!"))
                emit(Resource.Loading(isLoading = false))
                return@flow
            } catch (e: HttpException) {
                emit(Resource.Error(errorMessage = e.localizedMessage ?: "Couldn't load data!"))
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            mediaList?.let {
                val remoteMedia = mediaList.map { it.toMedia(type, category) }
                val entities = mediaList.map { it.toMediaEntity(type, category) }

                dao.insertMedias(entities)
                emit(Resource.Success(data = remoteMedia))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getTrendingAnything(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMedias = dao.getTrendingMedias(TRENDING)

            val shouldLoadCache =
                localMedias.isNotEmpty() && !fetchFromRemote && !isRefresh

            if (shouldLoadCache) {
                emit(Resource.Success(data = localMedias.map { it.toMedia(type, TRENDING) }))
                emit(Resource.Loading(true))
                return@flow
            }

            var searchPage = page

            if (isRefresh) {
                dao.deleteTrendingMedia(TRENDING)
                searchPage = 1
            }

            val mediaList = try {
                api.getTrendingAnything(type, time, searchPage, apiKey).medias
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "Couldn't load data!"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Couldn't load data!"))
                emit(Resource.Loading(false))
                return@flow
            }

            mediaList?.let {
                val media = mediaList.map { it.toMedia(type, TRENDING) }
                val entities = mediaList.map { it.toMediaEntity(type, TRENDING) }
                dao.insertMedias(entities)
                emit(Resource.Success(data = media))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getSearchAnything(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaListSearch = try {
                api.getSearchAnything(query, page, apiKey).medias
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            mediaListSearch?.let {
                val mediaToSearch = it.map { media ->
                    media.toMedia(
                        type = media.mediaType,
                        category = media.category
                    )
                }

                emit(Resource.Success(data = mediaToSearch))
                emit(Resource.Loading(false))
            }
        }
    }
}