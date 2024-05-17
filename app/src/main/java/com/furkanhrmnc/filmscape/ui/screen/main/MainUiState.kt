package com.furkanhrmnc.filmscape.ui.screen.main

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.util.ViewState

/**
 * Main Ui kısmında değişmesi muhtemel ui verileri bu sınıf içerisinde toplanır.
 */
data class MainUiState(
    val popularViewState: ViewState<List<Movie>> = ViewState.Loading,
    val nowPlayingViewState: ViewState<List<Movie>> = ViewState.Loading,
    val upComingViewState: ViewState<List<Movie>> = ViewState.Loading,
    val topRatedViewState: ViewState<List<Movie>> = ViewState.Loading,
    val error: Throwable? = null
)