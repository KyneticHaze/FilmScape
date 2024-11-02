package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.domain.model.MediaDetail

data class DetailsUiState(
    val mediaDetail: MediaDetail? = null,
    val recommendedMedias: List<Media> = emptyList(),
    val videoKey: String? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
)
