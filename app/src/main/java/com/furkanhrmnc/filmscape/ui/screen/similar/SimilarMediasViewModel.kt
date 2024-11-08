package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class SimilarMediasViewModel(
    id: Int,
    pager: SimilarMediasPager,
) : ViewModel() {

    var error = MutableStateFlow<Throwable?>(null)
        private set

    val similarMedias = pager.similarPagingDataFlow(
        type = MediaType.MOVIE.name.lowercase(),
        id = id
    )
        .catch { throwable -> onError(throwable) }
        .cachedIn(viewModelScope)

    fun onError(throwable: Throwable) {
        error.value = throwable
    }

    fun onErrorConsumed() {
        error.value = null
    }
}