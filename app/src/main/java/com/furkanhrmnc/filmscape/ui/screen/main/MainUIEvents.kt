package com.furkanhrmnc.filmscape.ui.screen.main

sealed class MainUIEvents {
    data class Refresh(val type: String): MainUIEvents()

    data class Paginate(val type: String): MainUIEvents()
}