package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.domain.model.PaginatedData
import com.furkanhrmnc.filmscape.util.Resource

sealed class ViewState<out T> {
    data object Loading: ViewState<Nothing>()
    data class Success<T>(val data: T): ViewState<T>()
    data class Failure(val exception: Throwable?): ViewState<Nothing>()
}

fun <T> Resource<T>.toViewState(): ViewState<T> = when(this) {
    is Resource.Error -> ViewState.Failure(exception = this.errorMessage)
    Resource.Loading -> ViewState.Loading
    is Resource.Success -> ViewState.Success(data = this.data)
}

fun <T> Resource<PaginatedData<T>>.toViewPaginated():ViewState<List<T>> = when(this) {
    is Resource.Error -> ViewState.Failure(exception = this.errorMessage)
    Resource.Loading -> ViewState.Loading
    is Resource.Success -> ViewState.Success(data = this.data.results)
}