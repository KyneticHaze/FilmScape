package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.util.Constants.SUBSCRIBED_MS
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SearchMediasViewModel(pager: SearchMediasPager) : ViewModel() {

    var error = MutableStateFlow<Throwable?>(null)
        private set

    val searchMedias = pager.searchPagingDataFlow(MediaType.MOVIE.name.lowercase())
        .catch { throwable -> onError(throwable) }
        .cachedIn(viewModelScope)

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
        .onEach(pager::onQuery)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            _search.value
        )

    fun onSearch(search: String) {
        _search.value = search
    }

    fun onClear() {
        _search.value = ""
    }


    fun onError(throwable: Throwable) {
        error.value = throwable
    }

    fun onErrorConsumed() {
        error.value = null
    }
}