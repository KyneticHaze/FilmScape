package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.ui.screen.main.medias.MediaPager
import com.furkanhrmnc.filmscape.util.Category
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class MoviesViewModel(pager: MediaPager) : ViewModel() {

    var error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
        private set

    val popularMovies = pager.getMediaDataWithPaging(
        type = MediaType.MOVIE.lowerName,
        category = Category.POPULAR
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    val topRatedMovies = pager.getMediaDataWithPaging(
        type = MediaType.MOVIE.lowerName,
        category = Category.TOP_RATED
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    val upcomingMovies = pager.getMediaDataWithPaging(
        type = MediaType.MOVIE.lowerName,
        category = Category.UPCOMING
    )
        .catch { t -> onError(t) }
        .cachedIn(viewModelScope)

    val nowPlayingMovies = pager.getMediaDataWithPaging(
        type = MediaType.MOVIE.lowerName,
        category = Category.NOW_PLAYING
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