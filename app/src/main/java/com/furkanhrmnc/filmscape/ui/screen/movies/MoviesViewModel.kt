package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.util.Category
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoviesViewModel(category: Category, pager: MoviesPager): ViewModel() {

    val movies = pager.pagingDataFlow(category = category)
        .catch {throwable -> onError(throwable) }
        .cachedIn(viewModelScope)


    var error by mutableStateOf<Throwable?>(null)
        private set

    fun onError(error: Throwable) {
        viewModelScope.launch {
            this@MoviesViewModel.error = error
        }
    }

    fun errorConsumed() {
        viewModelScope.launch {
            error = null
        }
    }
}