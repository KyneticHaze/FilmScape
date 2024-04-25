package com.furkanhrmnc.filmscape.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Failure(val throwable: Throwable) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> { value -> Resource.Success(data = value) }
        .onStart { emit(Resource.Loading) }
        .catch { throwable -> emit(Resource.Failure(throwable = throwable)) }
}