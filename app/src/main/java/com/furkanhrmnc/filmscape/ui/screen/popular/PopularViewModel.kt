package com.furkanhrmnc.filmscape.ui.screen.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch

class PopularViewModel(pager: PopularPager) : ViewModel() {

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error

    val popular = pager.pagingDataOnFlow(MediaType.MOVIE.name)
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    fun onError(t: Throwable) {
        _error.value = t
    }

    fun onErrorConsumed() {
        _error.value = null
    }
}