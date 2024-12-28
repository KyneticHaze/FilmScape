package com.furkanhrmnc.filmscape.ui.screen.tv

import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class TvUiState(
    val topRatedTvSeries: Flow<PagingData<Media>> = emptyFlow(),
    val popularTvSeries: Flow<PagingData<Media>> = emptyFlow(),
    val error: Throwable? = null,
    val isLoading: Boolean = false,
)