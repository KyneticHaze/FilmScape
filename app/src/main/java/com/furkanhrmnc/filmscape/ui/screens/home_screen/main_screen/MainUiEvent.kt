package com.furkanhrmnc.filmscape.ui.screens.home_screen.main_screen

sealed class MainUiEvent {
    data class Paginate(val type : String): MainUiEvent()
    data class Refresh(val type : String): MainUiEvent()
}
