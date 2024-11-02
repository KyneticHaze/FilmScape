package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.Media

sealed class DetailUiEvent {
    data class AddFavorite(val media: Media) : DetailUiEvent()
    data class PlayVideo(val videoId: String) : DetailUiEvent()
    data class Navigate(val route: String) : DetailUiEvent()
}