package com.furkanhrmnc.filmscape.ui.screen.movies

import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieUiState(
    val popularMovies: Flow<PagingData<Media>> = emptyFlow(),
    val topRatedMovies: Flow<PagingData<Media>> = emptyFlow(),
    val upcomingMovies: Flow<PagingData<Media>> = emptyFlow(),
    val nowPlayingMovies: Flow<PagingData<Media>> = emptyFlow(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
)