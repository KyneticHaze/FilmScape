package com.furkanhrmnc.filmscape.util

import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.ViewState
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState

fun Category.categoryResId(): Int = when(this) {
    Category.POPULAR -> R.string.popular
    Category.TOP_RATED -> R.string.top_rated
    Category.UP_COMING -> R.string.up_coming
    Category.NOW_PLAYING -> R.string.now_playing
}

fun MainUIState.moviesState(category: Category): ViewState<List<Movie>> = when(category) {
    Category.POPULAR -> this.popularViewState
    Category.TOP_RATED -> this.topRatedViewState
    Category.UP_COMING -> this.upComingViewState
    Category.NOW_PLAYING -> this.nowPlayingViewState
}