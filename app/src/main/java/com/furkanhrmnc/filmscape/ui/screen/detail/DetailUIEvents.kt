package com.furkanhrmnc.filmscape.ui.screen.detail

sealed class DetailUIEvents {

    data object WatchVideo: DetailUIEvents()
    data object Refresh: DetailUIEvents()

    data class SetDataAndLoad(
        val id: Int,
        val type: String,
        val category: String
    ) : DetailUIEvents()
}