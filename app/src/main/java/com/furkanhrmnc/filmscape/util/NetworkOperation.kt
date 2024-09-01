package com.furkanhrmnc.filmscape.util

import com.furkanhrmnc.filmscape.util.NetworkOperation.Failure
import com.furkanhrmnc.filmscape.util.NetworkOperation.Loading
import com.furkanhrmnc.filmscape.util.NetworkOperation.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Bu arayüz, data fetch işleminde response kısmını, bir tip ile değerlendirecek.
 *
 * [Success] içerdiği [Success.data] ile tipi generic olan veriyi verecek
 *
 * [Failure] data fetch'de hata varsa bir hata fırlatacak
 *
 * [Loading] aslında bir obje. Ve Data fetch esnasında bulunacak
 *
 * @author Furkan Harmancı
 */
sealed interface NetworkOperation<out T> {
    data class Success<T>(val data: T) : NetworkOperation<T>
    data class Failure(val throwable: Throwable) : NetworkOperation<Nothing>
    data object Loading : NetworkOperation<Nothing>
}

fun <T> Flow<T>.asNetworkOperationFlowResult(): Flow<NetworkOperation<T>> {
    return this
        .map<T, NetworkOperation<T>> { value -> Success(data = value) }
        .onStart { emit(Loading) }
        .catch { throwable -> emit(Failure(throwable = throwable)) }
}