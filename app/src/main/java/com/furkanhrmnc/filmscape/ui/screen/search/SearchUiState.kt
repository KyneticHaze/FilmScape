package com.furkanhrmnc.filmscape.ui.screen.search

import androidx.paging.PagingData
import com.furkanhrmnc.filmscape.domain.model.Media

data class SearchUiState(
    val query: String = "",
    val searchMedias: PagingData<Media> = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)