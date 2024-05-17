package com.furkanhrmnc.filmscape.util

import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.ui.screen.main.MainUiState

/**
 * Film kategorisine göre bir Int değeri dönecek.
 *
 * @return [Int]
 *
 * @author Furkan Harmancı
 */
fun Category.categoryResId(): Int = when(this) {
    Category.POPULAR -> R.string.popular
    Category.TOP_RATED -> R.string.top_rated
    Category.UPCOMING -> R.string.upcoming
    Category.NOW_PLAYING -> R.string.now_playing
}

/**
 * [Category] tipine göre bir film listesi dönmesini sağladığım fonksiyondur.
 *
 * @param category
 *
 * @return [ViewState]
 *
 * @author Furkan Harmancı
 */
fun MainUiState.getAllMoviesState(category: Category): ViewState<List<Movie>> = when(category) {
    Category.POPULAR -> this.popularViewState
    Category.TOP_RATED -> this.topRatedViewState
    Category.UPCOMING -> this.upComingViewState
    Category.NOW_PLAYING -> this.nowPlayingViewState
}