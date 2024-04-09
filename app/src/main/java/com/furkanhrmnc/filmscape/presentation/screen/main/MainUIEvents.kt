package com.furkanhrmnc.filmscape.presentation.screen.main

sealed class MainUIEvents {
    data class Refresh(val type: String): MainUIEvents()
    data class Paginate(val type: String): MainUIEvents()
}