package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SearchMoviesViewModel(pager: SearchMoviesPager) : ViewModel() {

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
        .onEach(pager::onQuery)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _search.value
        )

    val searchMovies = pager.searchPagingDataFlow
        .catch { onError(it) }
        .cachedIn(viewModelScope)

    fun onSearch(search: String) {
        _search.tryEmit(search)
    }

    fun onClear() {
        _search.tryEmit("")
    }


    private fun onError(error: Throwable) {
        _error.tryEmit(error)
    }
}