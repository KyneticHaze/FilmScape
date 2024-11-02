package com.furkanhrmnc.filmscape.ui.screen.details

sealed class DetailUiEvent {
    data class AddFavorite(val movie: Movie) : DetailUiEvent()
    data class PlayVideo(val videoId: String) : DetailUiEvent()
    data class Navigate(val route: String) : DetailUiEvent()
}