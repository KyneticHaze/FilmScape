package com.furkanhrmnc.filmscape.ui.screen.main

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.usecase.ViewState

data class MainUIState(
    val popularViewState: ViewState<List<Movie>> = ViewState.Loading,
    val nowPlayingViewState: ViewState<List<Movie>> = ViewState.Loading,
    val upComingViewState: ViewState<List<Movie>> = ViewState.Loading,
    val topRatedViewState: ViewState<List<Movie>> = ViewState.Loading,
    val error: Throwable? = null
)