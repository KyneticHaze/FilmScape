package com.furkanhrmnc.filmscape.ui.screens.detail

sealed class DetailEvents {
    data object Refresh: DetailEvents()

    data class SetDataOnLoad(val id: Int): DetailEvents()
}
