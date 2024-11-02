package com.furkanhrmnc.filmscape.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.ui.screen.main.movies.MediaPager
import com.furkanhrmnc.filmscape.util.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class MainViewModel(
    type: String,
    category: Category,
    pager: MediaPager,
) : ViewModel() {

    var error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
        private set

    val medias = pager.getMediaDataWithPaging(
        type = type,
        category = category
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    private fun onError(t: Throwable) {
        error.value = t
    }

    fun onErrorConsumed() {
        error.value = null
    }
}