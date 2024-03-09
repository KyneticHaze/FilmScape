package com.furkanhrmnc.filmscape.ui.screen.detail

import com.furkanhrmnc.filmscape.domain.model.Media

data class DetailUIState(

    val error: String = "",
    val isLoading: Boolean = false,

    val time: String = "",

    val videoKey: String = "",

    val media: Media? = null,
    val similarMediaList: List<Media> = emptyList(),
    val videoList: List<String> = emptyList()
)
