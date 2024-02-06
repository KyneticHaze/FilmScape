package com.example.movieland.ui.screens.home_screen.detail_screen

import com.example.movieland.data.remote.dto.image.Poster
import com.example.movieland.domain.model.Media

data class DetailUiState(
    val isLoading: Boolean = false,

    val errorMessage: String = "",

    val media: Media? = null,

    val posterList: List<Poster> = emptyList(),

    val similarMediaList: List<Media> = emptyList()
)
