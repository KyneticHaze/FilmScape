package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.PagingMovie
import com.furkanhrmnc.filmscape.util.NetworkOperation

/**
 * [NetworkOperation] ile data fetch handle etme işlemini ui kısmında tekrar işleyecek sınıftır.
 *
 * [NetworkOperation]'da olan şeylerin aynısı geçerlidir.
 */
sealed class ViewState<out T> {
    data object Loading: ViewState<Nothing>()
    data class Success<T>(val data: T): ViewState<T>()
    data class Failure(val throwable: Throwable?): ViewState<Nothing>()
}

/**
 * Tekli veri için [NetworkOperation] işlemlerini mapleyecek fonksiyondur.
 */
fun <T> NetworkOperation<T>.toViewState(): ViewState<T> = when(this) {
    is NetworkOperation.Failure -> ViewState.Failure(throwable = this.throwable)
    NetworkOperation.Loading -> ViewState.Loading
    is NetworkOperation.Success -> ViewState.Success(data = this.data)
}

/**
 * [NetworkOperation] işlemlerini [ViewState]'e maplerken liste halinde olmasını sağlayan fonksiyondur.
 */
fun <T> NetworkOperation<PagingMovie<T>>.toViewPaginated():ViewState<List<T>> = when(this) {
    is NetworkOperation.Failure -> ViewState.Failure(throwable = this.throwable)
    NetworkOperation.Loading -> ViewState.Loading
    is NetworkOperation.Success -> ViewState.Success(data = this.data.results)
}