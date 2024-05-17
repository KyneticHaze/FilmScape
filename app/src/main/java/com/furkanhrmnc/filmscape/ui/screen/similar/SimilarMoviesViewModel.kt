package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SimilarMoviesViewModel(pager: SimilarMoviesPager): ViewModel() {

    var error by mutableStateOf<Throwable?>(null)
        private set

    val movies = pager.similarPagingDataFlow
        .catch { error -> onError(error) }
        .cachedIn(viewModelScope)

    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@SimilarMoviesViewModel.error = error
        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch {
            error = null
        }
    }
}