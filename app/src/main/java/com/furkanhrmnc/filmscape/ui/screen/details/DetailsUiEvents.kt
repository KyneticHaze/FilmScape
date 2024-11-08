package com.furkanhrmnc.filmscape.ui.screen.details

import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.MediaType

sealed class DetailsUiEvents {
    data class AddFavorite(val uid: String, val media: Media) : DetailsUiEvents()
    data class SetDataAndLoad(
        val id: Int,
        val type: MediaType,
    ) : DetailsUiEvents()

    data class Navigate(val route: String) : DetailsUiEvents()
}