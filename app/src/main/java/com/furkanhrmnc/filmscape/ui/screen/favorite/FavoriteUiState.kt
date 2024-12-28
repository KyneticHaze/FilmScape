package com.furkanhrmnc.filmscape.ui.screen.favorite

import com.furkanhrmnc.filmscape.domain.model.Media

data class FavoriteUiState(
    val favorites: List<Media> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)