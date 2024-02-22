package com.furkanhrmnc.filmscape.ui.screens.detail_screen

import com.furkanhrmnc.filmscape.data.remote.dto.images.Poster
import com.furkanhrmnc.filmscape.domain.model.Media

data class DetailUiState(
    val isLoading: Boolean = false,

    val errorMessage: String = "",

    val media: Media? = null,

    val posterList: List<Poster> = emptyList(),

    val similarMediaListModel: List<Media> = emptyList()
)
