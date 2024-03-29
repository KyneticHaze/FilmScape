package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.common.Resource
import com.furkanhrmnc.filmscape.data.local.MediaDao
import com.furkanhrmnc.filmscape.data.mappers.toMedia
import com.furkanhrmnc.filmscape.data.mappers.toMediaEntity
import com.furkanhrmnc.filmscape.data.remote.api.MediaApi
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
    private val api: MediaApi,
    private val dao: MediaDao
) : MediaRepository {

    override suspend fun getMedia(id: Int): Media =
        dao.getMediaById(id).toMedia()

    override suspend fun insertMedia(media: Media) {
        val mediaEntity = media.toMediaEntity()
        dao.insertMedia(mediaEntity)
    }

    override suspend fun getMedias(
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val mediaList = try {
                /// 1'e endekslenen sayfayı veri çekme fonksiyonunda yerleştir.
                /// sayfa yenilenirse 1 yenilenmez ise verilen sayfadan veri çeker.
                api.getMedias(type, category, page, apiKey).medias
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
                val remoteMedia = mediaList.map { it.toMedia() }
                val entities = mediaList.map { it.toMediaEntity() }

                dao.insertMedias(entities)
                emit(Resource.Success(data = remoteMedia))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getTrendingMedias(
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaList = try {
                api.getTrendingMedias(type, time, page, apiKey).medias
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
                val media = mediaList.map { it.toMedia() }
                val entities = mediaList.map { it.toMediaEntity() }
                dao.insertMedias(entities)
                emit(Resource.Success(data = media))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun search(
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaListSearch = try {
                api.search(query, page, apiKey).medias
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
                    media.toMedia()
                }

                emit(Resource.Success(data = mediaToSearch))
                emit(Resource.Loading(false))
            }
        }
    }
}