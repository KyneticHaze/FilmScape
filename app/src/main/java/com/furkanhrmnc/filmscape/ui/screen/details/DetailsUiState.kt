package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.details.MovieDetails
import com.furkanhrmnc.filmscape.domain.usecase.ViewState

data class DetailsUiState(
    val detail: ViewState<MovieDetails> = ViewState.Loading,
    val error: Throwable? = null
)
