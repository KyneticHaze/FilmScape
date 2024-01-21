package com.example.movieland.data.repository

import com.example.movieland.core.Resource
import com.example.movieland.data.mappers.toMedia
import com.example.movieland.data.remote.api.MediaApi
import com.example.movieland.domain.model.Media
import com.example.movieland.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
    private val api: MediaApi
) : MediaRepository {
    override suspend fun getAnythings(
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val mediaList = try {
                api.getAnythings(type, category, page, apiKey).medias
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
                val media = mediaList.map {
                    it.toMedia()
                }
                emit(Resource.Success(data = media))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getTrendingAnything(
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val mediaList = try {
                api.getTrendingAnything(type, time, page, apiKey).medias
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
                val media = mediaList.map {
                    it.toMedia()
                }
                emit(Resource.Success(data = media))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getSearchAnything(
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
                    media.toMedia()
                }

                emit(Resource.Success(data = mediaToSearch))
                emit(Resource.Loading(false))
            }
        }
    }
}