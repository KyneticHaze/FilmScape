package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.Movie
import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.util.ViewState

data class DetailsUiState(
    val movieDetail: ViewState<MovieDetails> = ViewState.Loading,
    val recommendedMovies: ViewState<List<Movie>> = ViewState.Loading,
    val error: Throwable? = null
)
