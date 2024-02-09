package com.example.movieland.ui.screens.home_screen.detail_screen

sealed class DetailEvents {
    data object Refresh: DetailEvents()

    data class SetDataOnLoad(val id: Int): DetailEvents()
}