package com.furkanhrmnc.filmscape.util

import com.furkanhrmnc.filmscape.util.Response.Failure
import com.furkanhrmnc.filmscape.util.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Bu arayüz, data fetch işleminde response kısmını, bir tip ile değerlendirecek.
 *
 * [Success] içerdiği [Success.data] ile tipi generic olan veriyi verecek
 *
 * [Failure] data fetch'de hata varsa bir hata fırlatacak
 *
 *
 * @author Furkan Harmancı
 */
sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure(val throwable: Throwable) : Response<Nothing>()
}

fun <T> Flow<T>.asResponse(): Flow<Response<T>> {
    return this
        .map<T, Response<T>> { value -> Success(data = value) }
        .catch { throwable -> emit(Failure(throwable = throwable)) }
}

suspend fun <T> Flow<Response<T>>.getDataOrNull(): T? =
    this.firstOrNull { it is Success }?.let { (it as Success).data }