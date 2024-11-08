package com.furkanhrmnc.filmscape.ui.screen.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.ui.screen.main.medias.MediaPager
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class TvViewModel(pager: MediaPager) : ViewModel() {

    var error = MutableStateFlow<Throwable?>(null)
        private set

    val topRatedTvSeries = pager.getMediaDataWithPaging(
        type = MediaType.TV.lowerName,
        category = Category.TOP_RATED
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    val popularTvSeries = pager.getMediaDataWithPaging(
        type = MediaType.TV.lowerName,
        category = Category.POPULAR
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)


    private fun onError(throwable: Throwable) {
        error.value = throwable
    }

    fun onErrorConsumed() {
        error.value = null
    }
}