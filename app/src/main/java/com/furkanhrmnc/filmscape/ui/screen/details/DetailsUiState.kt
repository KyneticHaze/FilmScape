package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.Cast
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail

data class DetailsUiState(
    val mediaDetail: MediaDetail? = null,
    val recommendedMovies: List<Media> = emptyList(),
    val movieCasts: List<Cast> = emptyList(),
    val videoId: String = "",
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
)
