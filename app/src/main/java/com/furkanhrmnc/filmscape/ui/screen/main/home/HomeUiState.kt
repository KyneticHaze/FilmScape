package com.furkanhrmnc.filmscape.ui.screen.main.home

import com.furkanhrmnc.filmscape.domain.model.Media

data class HomeUiState(
    val trendingMedias: List<Media> = emptyList(),
    val onTheAirCarousel: List<Media> = emptyList(),
    val movie: Media? = null,
    val tvSeries: Media? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)