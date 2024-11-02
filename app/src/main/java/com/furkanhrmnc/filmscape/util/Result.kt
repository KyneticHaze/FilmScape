package com.furkanhrmnc.filmscape.util

import com.furkanhrmnc.filmscape.util.Result.Failure
import com.furkanhrmnc.filmscape.util.Result.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val throwable: Throwable) : Result<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { value -> Success(data = value) }
        .catch { throwable -> emit(Failure(throwable = throwable)) }
}